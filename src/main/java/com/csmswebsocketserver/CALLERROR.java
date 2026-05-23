/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.csmswebsocketserver;

import enumdatatype.*;
import javax.json.JsonObject;

/**
 *
 * @author Shubham
 */
public class CALLERROR extends WebsocketMessage {
       
    private final RPCErrorCodes ErrorCode ;
    private final String ErrorDescription ;
    private final JsonObject ErrorDetails ;
    public static String MessageId ;
    private final String messageId;

    public CALLERROR(RPCErrorCodes ErrorCode ,String ErrorDescription ,JsonObject ErrorDetails){
        this(CALLERROR.MessageId, ErrorCode, ErrorDescription, ErrorDetails);
    }

    public CALLERROR(String MessageId, RPCErrorCodes ErrorCode ,String ErrorDescription ,JsonObject ErrorDetails){
        CALLERROR.MessageId = MessageId;
        this.messageId = MessageId;
        this.ErrorCode = ErrorCode ;
        this.ErrorDescription = ErrorDescription ;
        this.ErrorDetails = ErrorDetails ;
    }

    public RPCErrorCodes getErrorCode(){
        return this.ErrorCode ;
    }
    public String getErrorDescription(){
        return this.ErrorDescription ;
    }
    public JsonObject getErrorDetails() {
        return this.ErrorDetails ;}

    public String getMessageId() {
        return messageId;
    }

    }
