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
import java.util.UUID;
import javax.json.JsonObject;


public class CALL extends WebsocketMessage {

    public static String Action ;
    private final JsonObject Payload ;
    public static String MessageId ;

    public CALL(String Action,JsonObject Payload){
        CALL.Action = Action ;
        this.Payload = Payload ;
    }

    public static void setMessageId() {
        MessageId = UUID.randomUUID().toString();
    }

    public JsonObject getPayload() { return this.Payload ;}
    
}