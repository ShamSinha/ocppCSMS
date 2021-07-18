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
   /* private UUIDgenerator uuiDgenerator = new UUIDgenerator();
    private Session session ;
    public SendRequestToCS(Session session){
        this.session = session ;
    }
    
    public void sendChangeAvailabilityRequest(OperationalStatusEnumType operationalStatus, EVSEType evse) throws IOException, EncodeException{
        ChangeAvailabilityRequest changeAvailability = new ChangeAvailabilityRequest(operationalStatus,evse);
        CALL call = new CALL(2,uuiDgenerator.uuid(),"ChangeAvailability",changeAvailability.setpayload());
        this.session.getBasicRemote().sendObject(call);
    }
    
    public void sendCostUpdateRequest(float totalCost, String transactionId) throws IOException, EncodeException{
        CostUpdateRequest costUpdate = new CostUpdateRequest(totalCost,transactionId);
        CALL call = new CALL(2,uuiDgenerator.uuid(),"CostUpdate",costUpdate.setpayload());
        this.session.getBasicRemote().sendObject(call);
    }
    
    public void sendResetRequest(ResetEnumType r ,int evseId) throws IOException, EncodeException{
        ResetRequest reset = new ResetRequest(r,evseId);
        CALL call = new CALL(2,uuiDgenerator.uuid(),"Reset",reset.setpayload());
        this.session.getBasicRemote().sendObject(call);
    }
    
    public void sendSetDisplayMessageRequest(MessageInfoType m) throws IOException, EncodeException{
        SetDisplayMessageRequest  setDisplayMessage = new SetDisplayMessageRequest(m);
        CALL call = new CALL(2,uuiDgenerator.uuid(),"SetDisplayMessage",setDisplayMessage.setpayload());
        this.session.getBasicRemote().sendObject(call);
    }
    
    public void sendSetVariablesRequest(SetVariableDataType setVariable) throws IOException, EncodeException{
        SetVariablesRequest setVariables = new SetVariablesRequest(setVariable);
        CALL call = new CALL(2,uuiDgenerator.uuid(),"SetVariables",setVariables.setpayload());
        this.session.getBasicRemote().sendObject(call);   
    }
    
    public void sendGetVariablesRequest(GetVariableDataType getVariable) throws IOException, EncodeException{
        GetVariablesRequest getVariables = new GetVariablesRequest(getVariable);
        CALL call = new CALL(2,uuiDgenerator.uuid(),"GetVariables",getVariables.setpayload());
        this.session.getBasicRemote().sendObject(call);   
    }
    
    public void sendGetBaseReportRequest(int requestId, ReportBaseEnumType reportBase) throws IOException, EncodeException{
        GetBaseReportRequest getBaseReport = new GetBaseReportRequest(requestId, reportBase);
        CALL call = new CALL(2,uuiDgenerator.uuid(),"GetBaseReport",getBaseReport.setpayload());
        this.session.getBasicRemote().sendObject(call);   
    }
    
    */
}
