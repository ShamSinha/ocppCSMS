/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.csmswebsocketserver;

/**
 *
 * @author Shubham
 * */
import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;


public class MessageEncodeResult implements Encoder.Text<CALLRESULT> {
    private static final int CALLRESULT_MESSAGE_TYPE_ID = 3;

    @Override
    public String encode(CALLRESULT callresult) throws EncodeException {

        if (callresult.getMessageId() == null) {
            throw new EncodeException(callresult, "OCPP-J CALLRESULT requires a messageId");
        }

        JsonObject payload = callresult.getPayload() == null
                ? Json.createObjectBuilder().build()
                : callresult.getPayload();

        return Json.createArrayBuilder()
                .add(CALLRESULT_MESSAGE_TYPE_ID)
                .add(callresult.getMessageId())
                .add(payload)
                .build()
                .toString();

    }

    @Override
    public void init(EndpointConfig config) {

    }

    @Override
    public void destroy() {

    }
}
