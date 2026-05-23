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


public class MessageEncodeError implements Encoder.Text<CALLERROR> {
    private static final int CALLERROR_MESSAGE_TYPE_ID = 4;

    @Override
    public String encode(CALLERROR callerror) throws EncodeException {
        if (callerror.getMessageId() == null || callerror.getErrorCode() == null) {
            throw new EncodeException(callerror, "OCPP-J CALLERROR requires a messageId and errorCode");
        }

        JsonObject errorDetails = callerror.getErrorDetails() == null
                ? Json.createObjectBuilder().build()
                : callerror.getErrorDetails();

        return Json.createArrayBuilder()
                .add(CALLERROR_MESSAGE_TYPE_ID)
                .add(callerror.getMessageId())
                .add(callerror.getErrorCode().name())
                .add(callerror.getErrorDescription() == null ? "" : callerror.getErrorDescription())
                .add(errorDetails)
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
