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
import java.text.MessageFormat;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;


public class MessageEncodeError implements Encoder.Text<CALLERROR> {
    @Override
    public String encode(CALLERROR callerror) throws EncodeException {
        return MessageFormat.format("{0}#{1}#{2}#{3}#{4}", CALLERROR.MessageTypeId, CALLERROR.MessageId, callerror.getErrorCode(), callerror.getErrorDescription(),callerror.getErrorDetails());
    
    }

    @Override
    public void init(EndpointConfig config) {

    }

    @Override
    public void destroy() {

    }
}