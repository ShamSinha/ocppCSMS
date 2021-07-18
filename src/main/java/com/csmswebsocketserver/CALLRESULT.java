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


    public CALLRESULT(JsonObject Payload){
        this.Payload = Payload ;
    }
    public JsonObject getPayload() { return this.Payload ;}
}
    


