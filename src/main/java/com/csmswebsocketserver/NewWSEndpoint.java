/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.csmswebsocketserver;


import datatype.IdTokenInfoType;
import enumdatatype.AuthorizationStatusEnumType;
import enumdatatype.ChargingStateEnumType;
import enumdatatype.IdTokenEnumType;
import enumdatatype.ReadingContextEnumType;
import enumdatatype.RegistrationStatusEnumType;
import enumdatatype.TriggerReasonEnumType;
import serverrequest.ChangeAvailabilityRequest;
import serverresponse.* ;
import java.io.IOException;
import java.util.Collections;

import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;
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
        decoders = {MessageDecoderboth.class,},
        subprotocols = {"ocpp2.0.1","CSOProtocol", "appUserProtocol"},
        configurator = CSMSServerConfigurator.class)

public class NewWSEndpoint {
   
    private static final Set<Session> sessions = Collections.synchronizedSet(new HashSet<Session>());
    private static final Set<String> User = Collections.synchronizedSet(new HashSet<String>());

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
            break;
            default:
                StringTokenizer st = new StringTokenizer(client,"#"); // client = app#
                if("app".equals(st.nextToken())){
                    User.add(st.nextToken()+ "/" +session.getId());
                    sessions.add(session);
                }
                
        }
               
    }
    
    
    @OnMessage
    public void JSONfromThirdParties(JsonObject object , @PathParam("client") String client) {
        if("CSO".equals(client)){
            
        }
        
        StringTokenizer st = new StringTokenizer(client,"#"); // client = app#username   
        if("app".equals(st.nextToken())){
            
        }
        
    }
    
    @OnMessage
    public void onWebsocketMessage(WebsocketMessage msg, Session session, @PathParam("client") String client) throws EncodeException, IOException {
        if("CSO1".equals(client)){
            if(msg instanceof CALL){

               JsonObject payload = null ;
               switch(CALL.Action){
                    case "BootNotification":
                        BootNotificationResponse bootNotification = new BootNotificationResponse();
                        bootNotification.setCurrentTime();
                        bootNotification.setInterval(300);
                        bootNotification.setStatus(RegistrationStatusEnumType.Accepted);
                        bootNotification.setpayload();
                        payload = bootNotification.getpayload();
                        break;
                    case "Authorize":
                        JsonObject innerJsonObject = ((CALL) msg).getPayload().getJsonObject("idToken");
                        String idToken = innerJsonObject.getString("idToken");
                        IdTokenEnumType type = IdTokenEnumType.valueOf(innerJsonObject.getString("type"));
                        if((type == IdTokenEnumType.KeyCode && "1234".equals(idToken)) || (type == IdTokenEnumType.ISO14443 && "".equals(idToken) )){
                            
                            IdTokenInfoType idTokenInfo = new IdTokenInfoType();
                            idTokenInfo.setStatus(AuthorizationStatusEnumType.Accepted);
                            idTokenInfo.setCacheExpiryDateTime(4);
                            AuthorizeResponse authresponse = new AuthorizeResponse();
                            authresponse.setIdToken(idTokenInfo);
                            authresponse.setpayload();
                            payload = authresponse.getPayload();
                        }
                        break;
                    case "HeartBeat":
                        HeartBeatResponse heartbeat= new HeartBeatResponse();
                        heartbeat.setCurrentTime();
                        heartbeat.setpayload();
                        heartbeat.setpayload();
                        payload = heartbeat.getPayload();
                        break;
                    case "StatusNotification" :
                        StatusNotificationResponse statusNotification = new StatusNotificationResponse();
                        statusNotification.setpayload();
                        payload = statusNotification.getPayload();
                        break;
                    case "TransactionEvent" :
                        JsonObject j = ((CALL) msg).getPayload();
                        
                        TriggerReasonEnumType triggerReason = TriggerReasonEnumType.valueOf(j.getString("triggerReason"));
                        JsonObject innerJson = j.getJsonObject("transactionInfo");
                        ChargingStateEnumType chargingState = ChargingStateEnumType.valueOf(innerJson.getString("chargingState"));
                        TransactionEventResponse transactEvent = new TransactionEventResponse();
                   
                        
                        
                        if(triggerReason == TriggerReasonEnumType.MeterValuePeriodic && chargingState == ChargingStateEnumType.Charging){
                            JsonObject SampledValue = j.getJsonObject("meterValue").getJsonObject("sampledValue") ;
                            double value = SampledValue.getInt("value");
                            ReadingContextEnumType context = ReadingContextEnumType.valueOf(SampledValue.getString("context"));
                            if(context == ReadingContextEnumType.SamplePeriodic){
                                
                                transactEvent.setpayload(false,false);
                            }
                        }
                        
     
                        payload = transactEvent.getPayload();
                        break ;
                    case "ChangeAvailability" :
                        ChangeAvailabilityRequest c = new ChangeAvailabilityRequest();
                        c.setpayload();
                        payload = c.getPayload();
                        break;
               }
               CALLRESULT.MessageTypeId = 3 ;
               CALLRESULT.MessageId = CALL.MessageId ;
               CALLRESULT callresult = new CALLRESULT(payload);
               session.getBasicRemote().sendObject(callresult);
               
            }
            else if(msg instanceof CALLRESULT){


            }    
            else if(msg instanceof CALLERROR){

            }   
        }
        
    }
    @OnClose
    public void onClose(Session session) {
      sessions.remove(session);
    }
   
    
}
