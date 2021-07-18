/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.csmswebsocketserver;

import java.util.UUID;

/**
 *
 * @author Shubham
 */
public class UUIDgenerator {
    public String uuid() {
    // generating random UUID
        UUID gfg = UUID.randomUUID();
        return String.valueOf(gfg);
    }
}
