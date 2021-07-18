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

import java.util.List;
import javax.websocket.Extension;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

public class CSMSServerConfigurator extends ServerEndpointConfig.Configurator {

    @Override
    public <T> T getEndpointInstance(Class<T> endpointClass) throws InstantiationException {
        return super.getEndpointInstance(endpointClass); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
        super.modifyHandshake(sec, request, response); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean checkOrigin(String originHeaderValue) {
        return super.checkOrigin(originHeaderValue); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Extension> getNegotiatedExtensions(List<Extension> installed, List<Extension> requested) {
        return super.getNegotiatedExtensions(installed, requested); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getNegotiatedSubprotocol(List<String> supported, List<String> requested) {
        return super.getNegotiatedSubprotocol(supported, requested); //To change body of generated methods, choose Tools | Templates.
    }
    
    

}
