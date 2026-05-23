/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.csmswebsocketserver;

/**
 *
 * @author Shubham
 */
import javax.json.JsonObject;

public class CALLRESULT extends WebsocketMessage {

   private final JsonObject Payload ;
    public static String MessageId ;
    private final String messageId;


    public CALLRESULT(JsonObject Payload){
        this(CALLRESULT.MessageId, Payload);
    }

    public CALLRESULT(String MessageId, JsonObject Payload){
        CALLRESULT.MessageId = MessageId;
        this.messageId = MessageId;
        this.Payload = Payload ;
    }
    public JsonObject getPayload() { return this.Payload ;}

    public String getMessageId() {
        return messageId;
    }
}
    


