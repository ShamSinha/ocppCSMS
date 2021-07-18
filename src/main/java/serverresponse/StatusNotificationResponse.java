/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverresponse;

import javax.json.JsonObject;
import javax.json.JsonException;
/**
 *
 * @author Shubham
 */
public class StatusNotificationResponse {
    
    private JsonObject payload;
    public void setpayload() throws JsonException {
        payload = null ;
    }
    
    public JsonObject getPayload(){
        return payload;
    }
}
