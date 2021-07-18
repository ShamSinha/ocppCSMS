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
import enumdatatype.*;
import java.io.StringReader;
import java.nio.ByteBuffer;
import javax.json.JsonObject;
import java.util.StringTokenizer;
import javax.json.Json;
import javax.json.JsonReader;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;


public class MessageDecoderboth implements Decoder.Text<WebsocketMessage> , Decoder.Binary<JsonObject>{

    @Override
    public WebsocketMessage decode(String s) throws DecodeException {
        
        StringTokenizer st = new StringTokenizer(s,"#");
        int messagetypeId = Integer.parseInt(st.nextToken());
        if(messagetypeId == 2){
            String messageId = st.nextToken();
            String action = st.nextToken();
            JsonObject payload = jsonFromString(st.nextToken());
            CALL.MessageTypeId = messagetypeId ;
            CALL.MessageId = messageId ;
            return new CALL(action, payload) ;
        }
        
        if(messagetypeId == 3){
            String messageId = st.nextToken();
            JsonObject payload = jsonFromString(st.nextToken());
            CALLRESULT.MessageTypeId = messagetypeId ;
            CALLRESULT.MessageId = messageId ;
            return new CALLRESULT(payload) ;
        }
       if(messagetypeId == 4){
            String messageId = st.nextToken();
            RPCErrorCodes errorcode = RPCErrorCodes.valueOf(st.nextToken());
            String errordescription = st.nextToken();
            JsonObject errordetails  = jsonFromString(st.nextToken());
            CALLERROR.MessageTypeId = messagetypeId ;
            CALLERROR.MessageId = messageId ;

            return new CALLERROR(errorcode,errordescription, errordetails) ;
        }
         
        return null ;
       }
    private static JsonObject jsonFromString(String jsonObjectStr) {

        JsonObject object;
        try (JsonReader jsonReader = Json.createReader(new StringReader(jsonObjectStr))) {
            object = jsonReader.readObject();
        }

    return object;
    }

    @Override
    public boolean willDecode(String s) {
        StringTokenizer st = new StringTokenizer(s,"#");
        int messagetypeId = Integer.parseInt(st.nextToken());
        return messagetypeId == 2 || messagetypeId == 3 || messagetypeId == 4 ;
    }

    @Override
    public void init(EndpointConfig ec) {
        
    }

    @Override
    public void destroy() {
        
    }

    @Override
    public JsonObject decode(ByteBuffer bb) throws DecodeException {
        return null ;
    }

    @Override
    public boolean willDecode(ByteBuffer bb) {
        return false ;
    }
    
}
