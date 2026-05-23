/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.csmswebsocketserver;

import datatype.EVSEType;
import datatype.GetVariableDataType;
import datatype.MessageInfoType;
import datatype.SetVariableDataType;
import enumdatatype.OperationalStatusEnumType;
import enumdatatype.ReportBaseEnumType;
import enumdatatype.ResetEnumType;
import java.io.IOException;
import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.EncodeException;
import javax.websocket.Session;
import serverrequest.ChangeAvailabilityRequest;
import serverrequest.CostUpdateRequest;
import serverrequest.GetBaseReportRequest;
import serverrequest.GetVariablesRequest;
import serverrequest.ResetRequest;
import serverrequest.SetDisplayMessageRequest;
import serverrequest.SetVariablesRequest;

/**
 *
 * @author Shubham
 */
public class SendRequestToCS {

    public void send(Session session, String action, JsonObject payload) throws IOException, EncodeException {
        JsonObject safePayload = payload == null ? Json.createObjectBuilder().build() : payload;
        session.getBasicRemote().sendObject(new CALL(action, safePayload));
    }

    public void sendChangeAvailabilityRequest(Session session, OperationalStatusEnumType operationalStatus, EVSEType evse)
            throws IOException, EncodeException {
        ChangeAvailabilityRequest changeAvailability = new ChangeAvailabilityRequest();
        changeAvailability.setOperationalStatus(operationalStatus);
        changeAvailability.setEvse(evse);
        changeAvailability.setpayload();
        send(session, "ChangeAvailability", changeAvailability.getPayload());
    }

    public void sendCostUpdatedRequest(Session session, float totalCost, String transactionId)
            throws IOException, EncodeException {
        CostUpdateRequest costUpdate = new CostUpdateRequest();
        costUpdate.setTotalCost(totalCost);
        costUpdate.settransactionId(transactionId);
        costUpdate.setpayload();
        send(session, "CostUpdated", costUpdate.getPayload());
    }

    public void sendResetRequest(Session session, ResetEnumType resetType, int evseId)
            throws IOException, EncodeException {
        ResetRequest reset = new ResetRequest();
        reset.setType(resetType);
        reset.setEvseId(evseId);
        reset.setpayload();
        send(session, "Reset", reset.getPayload());
    }

    public void sendSetDisplayMessageRequest(Session session, MessageInfoType messageInfo)
            throws IOException, EncodeException {
        SetDisplayMessageRequest setDisplayMessage = new SetDisplayMessageRequest();
        setDisplayMessage.setpayload(messageInfo);
        send(session, "SetDisplayMessage", setDisplayMessage.getPayload());
    }

    public void sendSetVariablesRequest(Session session, SetVariableDataType setVariable)
            throws IOException, EncodeException {
        SetVariablesRequest setVariables = new SetVariablesRequest();
        setVariables.setpayload(setVariable);
        send(session, "SetVariables", setVariables.getPayload());
    }

    public void sendGetVariablesRequest(Session session, GetVariableDataType getVariable)
            throws IOException, EncodeException {
        GetVariablesRequest getVariables = new GetVariablesRequest();
        getVariables.setGetVariableData(getVariable);
        getVariables.setpayload();
        send(session, "GetVariables", getVariables.getPayload());
    }

    public void sendGetBaseReportRequest(Session session, int requestId, ReportBaseEnumType reportBase)
            throws IOException, EncodeException {
        GetBaseReportRequest getBaseReport = new GetBaseReportRequest();
        getBaseReport.setRequestId(requestId);
        getBaseReport.setReportBase(reportBase);
        getBaseReport.setpayload();
        send(session, "GetBaseReport", getBaseReport.getPayload());
    }
}
