/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverrequest;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import datatype.MessageInfoType;

/**
 *
 * @author Shubham
 */
public class SetDisplayMessageRequest {
    
    private JsonObject payload;

    public void setpayload(MessageInfoType messageInfo){
        
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder()
                .add("message",messageInfo.getp());
                        
        payload = objectBuilder.build();   
        
    }

    public JsonObject getPayload() {
        return payload;
    }
    
}
