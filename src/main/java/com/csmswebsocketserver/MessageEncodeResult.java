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
import java.text.MessageFormat;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;


public class MessageEncodeResult implements Encoder.Text<CALLRESULT> {
    @Override
    public String encode(CALLRESULT callresult) throws EncodeException {
        
        return MessageFormat.format("{0}#{1}#{2}", CALLRESULT.MessageTypeId,CALLRESULT.MessageId, callresult.getPayload());
    
    }

    @Override
    public void init(EndpointConfig config) {

    }

    @Override
    public void destroy() {

    }
}