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
import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class MessageEncoder implements Encoder.Text<CALL> {

    private static final int CALL_MESSAGE_TYPE_ID = 2;

    @Override
    public void init(EndpointConfig config) {
    }

    @Override
    public void destroy() {
    }

    @Override
    public String encode(CALL call)  throws EncodeException{

        if (call.getMessageId() == null || call.getAction() == null) {
            throw new EncodeException(call, "OCPP-J CALL requires a messageId and action");
        }

        JsonObject payload = call.getPayload() == null
                ? Json.createObjectBuilder().build()
                : call.getPayload();

        return Json.createArrayBuilder()
                .add(CALL_MESSAGE_TYPE_ID)
                .add(call.getMessageId())
                .add(call.getAction())
                .add(payload)
                .build()
                .toString();
    
    }
}
