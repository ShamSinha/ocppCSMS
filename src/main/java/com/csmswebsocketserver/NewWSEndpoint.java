/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.csmswebsocketserver;


import datatype.IdTokenInfoType;
import enumdatatype.AuthorizationStatusEnumType;
import enumdatatype.IdTokenEnumType;
import enumdatatype.RegistrationStatusEnumType;
import enumdatatype.RPCErrorCodes;
import serverresponse.* ;
import java.io.IOException;
import java.util.Collections;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.concurrent.ConcurrentHashMap;
import javax.json.Json;
import javax.websocket.EncodeException;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import javax.json.JsonObject ;
import javax.websocket.OnClose;
import javax.websocket.server.PathParam;
/**
 *
 * @author Shubham
 */
@ServerEndpoint(value = "/{client}",
        encoders = {MessageEncodeResult.class,MessageEncoder.class, MessageEncodeError.class},
        decoders = {MessageDecoderboth.class, JsonObjectTextDecoder.class},
        subprotocols = {"ocpp2.1", "ocpp2.0.1","CSOProtocol", "appUserProtocol"},
        configurator = CSMSServerConfigurator.class)

public class NewWSEndpoint {

    private static final Set<Session> sessions = Collections.synchronizedSet(new HashSet<Session>());
    private static final Set<String> User = Collections.synchronizedSet(new HashSet<String>());
    private static final Map<String, Session> chargingStations = new ConcurrentHashMap<String, Session>();
    private static final Map<String, PendingCsoRequest> pendingCsoRequests = new ConcurrentHashMap<String, PendingCsoRequest>();
    private static final Map<Integer, PendingCsoRequest> pendingDisplayMessageRequests = new ConcurrentHashMap<Integer, PendingCsoRequest>();

    @OnOpen
    public void initSession(Session session,@PathParam("client") String client) throws IOException {
        
        switch(client){
            case "Operator":
                User.add(client + "/" +session.getId());
                sessions.add(session);
            break;
            
            case "CS01":
                User.add(client + "/" +session.getId());
                sessions.add(session);
                chargingStations.put(client, session);
            break;
            default:
                StringTokenizer st = new StringTokenizer(client,"#"); // client = app#
                String clientType = st.hasMoreTokens() ? st.nextToken() : client;
                if("app".equals(clientType) && st.hasMoreTokens()){
                    User.add(st.nextToken()+ "/" +session.getId());
                    sessions.add(session);
                } else if (isChargingStation(client)) {
                    User.add(client + "/" + session.getId());
                    sessions.add(session);
                    chargingStations.put(client, session);
                }

        }

    }
    
    
    @OnMessage
    public void JSONfromThirdParties(JsonObject object, Session session, @PathParam("client") String client)
            throws IOException {
        if("CSO".equals(client) || "Operator".equals(client)){
            handleCsoMessage(object, session);
            return;
        }

        StringTokenizer st = new StringTokenizer(client,"#"); // client = app#username
        String clientType = st.hasMoreTokens() ? st.nextToken() : client;
        if("app".equals(clientType)){

        }
        
    }
    
    @OnMessage
    public void onWebsocketMessage(WebsocketMessage msg, Session session, @PathParam("client") String client) throws EncodeException, IOException {
        if(isChargingStation(client)){
            if(msg instanceof CALL){
               CALL call = (CALL) msg;
               try {
                   JsonObject payload = responsePayloadFor(call);
                   session.getBasicRemote().sendObject(new CALLRESULT(call.getMessageId(), payload));
                   relayNotifyDisplayMessages(call);
               } catch (OcppCallException e) {
                   sendCallError(session, call.getMessageId(), e.code, e.getMessage());
               } catch (RuntimeException e) {
                   sendCallError(session, call.getMessageId(), RPCErrorCodes.InternalError,
                           "CSMS could not process " + call.getAction());
               }

            }
            else if(msg instanceof CALLRESULT){
                relayCsoCallResult((CALLRESULT) msg);

            }
            else if(msg instanceof CALLERROR){
                relayCsoCallError((CALLERROR) msg);
            }
        }

    }

    private void handleCsoMessage(JsonObject object, Session session) throws IOException {
        String type = object.getString("type", "");
        if ("RfidUsersList".equals(type)) {
            sendJson(session, RfidUserRegistry.usersPayload());
            return;
        }

        if ("ForwardOcppCall".equals(type)) {
            forwardOcppCall(object, session);
            return;
        }

        if ("RfidUserUpsert".equals(type)) {
            String idToken = object.getString("idToken", "");
            if (idToken.trim().length() == 0) {
                sendCsoResult(session, false, "RFID token is required.");
                return;
            }

            try {
                IdTokenEnumType tokenType = IdTokenEnumType.valueOf(object.getString("tokenType", "ISO14443"));
                AuthorizationStatusEnumType status = AuthorizationStatusEnumType.valueOf(object.getString("status", "Accepted"));
                RfidUser user = RfidUserRegistry.upsert(
                        idToken,
                        tokenType,
                        object.getString("userName", "EV Driver"),
                        status);
                sendCsoResult(session, true, "Saved " + user.getIdToken());
                sendJson(session, RfidUserRegistry.usersPayload());
            } catch (IllegalArgumentException e) {
                sendCsoResult(session, false, "Unsupported token type or authorization status.");
            }
            return;
        }

        if ("RfidUserDelete".equals(type)) {
            String idToken = object.getString("idToken", "");
            boolean deleted = RfidUserRegistry.delete(idToken);
            sendCsoResult(session, deleted, deleted ? "Deleted " + RfidUserRegistry.normalize(idToken) : "Token not found.");
            sendJson(session, RfidUserRegistry.usersPayload());
            return;
        }

        sendCsoResult(session, false, "Unknown CSO message type: " + type);
    }

    private void forwardOcppCall(JsonObject object, Session csoSession) throws IOException {
        String chargingStationId = object.getString("chargingStationId", "CS01");
        String action = object.getString("action", "");
        if (action.trim().length() == 0) {
            sendOcppForwardResult(csoSession, false, "OCPP action is required.");
            return;
        }
        if (!OcppActionRegistry.isImplementedServerRequest(action)) {
            sendOcppForwardResult(csoSession, false, "CSMS forwarding is not enabled for " + action + ".");
            return;
        }

        Session chargingStationSession = chargingStations.get(chargingStationId);
        if (chargingStationSession == null || !chargingStationSession.isOpen()) {
            sendOcppForwardResult(csoSession, false, "Charging station " + chargingStationId + " is not connected.");
            return;
        }

        JsonObject payload = object.getJsonObject("payload");
        CALL call = new CALL(action, payload == null ? Json.createObjectBuilder().build() : payload);
        Integer displayRequestId = displayRequestId(action, call.getPayload());
        PendingCsoRequest pendingRequest = new PendingCsoRequest(csoSession, chargingStationId, action, displayRequestId);
        pendingCsoRequests.put(call.getMessageId(), pendingRequest);
        if (displayRequestId != null) {
            pendingDisplayMessageRequests.put(displayRequestId, pendingRequest);
        }
        try {
            chargingStationSession.getBasicRemote().sendObject(call);
            sendOcppForwardResult(csoSession, true, "Sent " + action + " to " + chargingStationId + ".");
        } catch (EncodeException e) {
            pendingCsoRequests.remove(call.getMessageId());
            if (displayRequestId != null) {
                pendingDisplayMessageRequests.remove(displayRequestId);
            }
            sendOcppForwardResult(csoSession, false, "Could not encode " + action + " for " + chargingStationId + ".");
        }
    }

    private void relayCsoCallResult(CALLRESULT callResult) throws IOException {
        PendingCsoRequest pending = pendingCsoRequests.remove(callResult.getMessageId());
        if (pending == null) {
            return;
        }
        if (!pending.session.isOpen()) {
            if (pending.displayRequestId != null) {
                pendingDisplayMessageRequests.remove(pending.displayRequestId);
            }
            return;
        }

        JsonObject resultPayload = callResult.getPayload();
        if (pending.displayRequestId != null
                && (resultPayload == null || !"Accepted".equals(resultPayload.getString("status", "")))) {
            pendingDisplayMessageRequests.remove(pending.displayRequestId);
        }

        sendJson(pending.session, Json.createObjectBuilder()
                .add("type", "OcppCallResult")
                .add("ok", true)
                .add("message", pending.action + " response from " + pending.chargingStationId + ".")
                .add("chargingStationId", pending.chargingStationId)
                .add("action", pending.action)
                .add("messageId", callResult.getMessageId())
                .add("payload", resultPayload == null ? Json.createObjectBuilder().build() : resultPayload)
                .build());
    }

    private void relayNotifyDisplayMessages(CALL call) throws IOException {
        if (!"NotifyDisplayMessages".equals(call.getAction()) || call.getPayload() == null) {
            return;
        }

        int requestId = call.getPayload().getInt("requestId", -1);
        PendingCsoRequest pending = pendingDisplayMessageRequests.get(requestId);
        if (pending == null) {
            return;
        }
        if (!pending.session.isOpen()) {
            pendingDisplayMessageRequests.remove(requestId);
            return;
        }

        sendJson(pending.session, Json.createObjectBuilder()
                .add("type", "OcppNotifyDisplayMessages")
                .add("ok", true)
                .add("message", "Display message data from " + pending.chargingStationId + ".")
                .add("chargingStationId", pending.chargingStationId)
                .add("action", call.getAction())
                .add("requestId", requestId)
                .add("payload", call.getPayload())
                .build());

        if (!call.getPayload().getBoolean("tbc", false)) {
            pendingDisplayMessageRequests.remove(requestId);
        }
    }

    private Integer displayRequestId(String action, JsonObject payload) {
        if (!"GetDisplayMessages".equals(action) || payload == null || !payload.containsKey("requestId")) {
            return null;
        }
        return payload.getInt("requestId");
    }

    private void relayCsoCallError(CALLERROR callError) throws IOException {
        PendingCsoRequest pending = pendingCsoRequests.remove(callError.getMessageId());
        if (pending == null) {
            return;
        }
        if (pending.displayRequestId != null) {
            pendingDisplayMessageRequests.remove(pending.displayRequestId);
        }
        if (!pending.session.isOpen()) {
            return;
        }

        sendJson(pending.session, Json.createObjectBuilder()
                .add("type", "OcppCallError")
                .add("ok", false)
                .add("message", pending.action + " failed on " + pending.chargingStationId + ": " + callError.getErrorDescription())
                .add("chargingStationId", pending.chargingStationId)
                .add("action", pending.action)
                .add("messageId", callError.getMessageId())
                .add("errorCode", callError.getErrorCode().name())
                .add("description", callError.getErrorDescription() == null ? "" : callError.getErrorDescription())
                .add("details", callError.getErrorDetails() == null ? Json.createObjectBuilder().build() : callError.getErrorDetails())
                .build());
    }

    private void sendOcppForwardResult(Session session, boolean ok, String message) throws IOException {
        sendJson(session, Json.createObjectBuilder()
                .add("type", "OcppForwardResult")
                .add("ok", ok)
                .add("message", message)
                .build());
    }

    private void sendCsoResult(Session session, boolean ok, String message) throws IOException {
        sendJson(session, Json.createObjectBuilder()
                .add("type", "RfidUserResult")
                .add("ok", ok)
                .add("message", message)
                .build());
    }

    private void sendJson(Session session, JsonObject object) throws IOException {
        session.getBasicRemote().sendText(object.toString());
    }

    private boolean isChargingStation(String client) {
        return !"Operator".equals(client)
                && !"CSO".equals(client)
                && !"CSO1".equals(client)
                && !client.startsWith("app#");
    }

    private JsonObject responsePayloadFor(CALL call) throws OcppCallException {
        String action = call.getAction();
        if (!OcppActionRegistry.isImplementedChargerRequest(action)) {
            RPCErrorCodes code = OcppActionRegistry.isKnownAction(action)
                    ? RPCErrorCodes.NotSupported
                    : RPCErrorCodes.NotImplemented;
            String description = OcppActionRegistry.isKnownAction(action)
                    ? "Action is recognized by OCPP 2.1 but not supported by this CSMS"
                    : "Action is not known by this CSMS";
            throw new OcppCallException(code, description);
        }

        switch(action){
            case "BootNotification":
                return bootNotificationPayload();
            case "Authorize":
                return authorizePayload(call.getPayload());
            case "Heartbeat":
                return heartbeatPayload();
            case "StatusNotification" :
                StatusNotificationResponse statusNotification = new StatusNotificationResponse();
                statusNotification.setpayload();
                return statusNotification.getPayload();
            case "TransactionEvent" :
                TransactionEventResponse transactEvent = new TransactionEventResponse();
                transactEvent.setpayload(false,false);
                return transactEvent.getPayload();
            case "DataTransfer" :
                return Json.createObjectBuilder()
                        .add("status", "Accepted")
                        .build();
            case "FirmwareStatusNotification" :
            case "LogStatusNotification" :
            case "MeterValues" :
            case "NotifyEvent" :
            case "NotifyReport" :
            case "NotifyDisplayMessages" :
            case "PublishFirmwareStatusNotification" :
            case "SecurityEventNotification" :
                return Json.createObjectBuilder().build();
            default:
                throw new OcppCallException(RPCErrorCodes.InternalError,
                        "Implemented action has no CSMS handler: " + action);
        }
    }

    private JsonObject bootNotificationPayload() {
        BootNotificationResponse bootNotification = new BootNotificationResponse();
        bootNotification.setCurrentTime();
        bootNotification.setInterval(300);
        bootNotification.setStatus(RegistrationStatusEnumType.Accepted);
        bootNotification.setpayload();
        return bootNotification.getpayload();
    }

    private JsonObject authorizePayload(JsonObject payload) throws OcppCallException {
        JsonObject innerJsonObject = payload == null ? null : payload.getJsonObject("idToken");
        if (innerJsonObject == null) {
            throw new OcppCallException(RPCErrorCodes.FormatViolation,
                    "Authorize request must include idToken");
        }

        String idToken = innerJsonObject.getString("idToken", null);
        String typeName = innerJsonObject.getString("type", null);
        if (idToken == null || typeName == null) {
            throw new OcppCallException(RPCErrorCodes.FormatViolation,
                    "Authorize idToken must include idToken and type");
        }

        IdTokenEnumType type;
        try {
            type = IdTokenEnumType.valueOf(typeName);
        } catch (IllegalArgumentException e) {
            throw new OcppCallException(RPCErrorCodes.PropertyConstraintViolation,
                    "Unsupported idToken type: " + typeName);
        }

        RfidUser rfidUser = RfidUserRegistry.find(idToken);
        IdTokenInfoType idTokenInfo = new IdTokenInfoType();
        if(rfidUser != null && rfidUser.getTokenType() == type){
            idTokenInfo.setStatus(rfidUser.getStatus());
            if (rfidUser.getStatus() == AuthorizationStatusEnumType.Accepted) {
                idTokenInfo.setCacheExpiryDateTime(4);
            }
        }
        else if(type == IdTokenEnumType.KeyCode && "1234".equals(idToken)){
            idTokenInfo.setStatus(AuthorizationStatusEnumType.Accepted);
            idTokenInfo.setCacheExpiryDateTime(4);
        }
        else {
            idTokenInfo.setStatus(AuthorizationStatusEnumType.Invalid);
        }

        AuthorizeResponse authresponse = new AuthorizeResponse();
        authresponse.setIdToken(idTokenInfo);
        authresponse.setpayload();
        return authresponse.getPayload();
    }

    private JsonObject heartbeatPayload() {
        HeartBeatResponse heartbeat= new HeartBeatResponse();
        heartbeat.setCurrentTime();
        heartbeat.setpayload();
        return heartbeat.getPayload();
    }

    private void sendCallError(Session session, String messageId, RPCErrorCodes code, String description)
            throws IOException, EncodeException {
        session.getBasicRemote().sendObject(new CALLERROR(
                messageId,
                code,
                description,
                Json.createObjectBuilder().build()));
    }

    private static class OcppCallException extends Exception {
        private final RPCErrorCodes code;

        OcppCallException(RPCErrorCodes code, String message) {
            super(message);
            this.code = code;
        }
    }

    private static class PendingCsoRequest {
        private final Session session;
        private final String chargingStationId;
        private final String action;
        private final Integer displayRequestId;

        PendingCsoRequest(Session session, String chargingStationId, String action, Integer displayRequestId) {
            this.session = session;
            this.chargingStationId = chargingStationId;
            this.action = action;
            this.displayRequestId = displayRequestId;
        }
    }

    @OnClose
    public void onClose(Session session) {
      sessions.remove(session);
      chargingStations.values().remove(session);
      pendingCsoRequests.values().removeIf(pending -> pending.session.equals(session));
      pendingDisplayMessageRequests.values().removeIf(pending -> pending.session.equals(session));
    }
   
    
}
