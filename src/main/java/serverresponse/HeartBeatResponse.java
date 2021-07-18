/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverresponse;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import datatype.dateTime;

/**
 *
 * @author Shubham
 */
public class HeartBeatResponse {
    
    private String currentTime;
    private JsonObject payload;
    
   
    public String setCurrentTime() {
        dateTime d = new dateTime() ;
        return d.dT();
    }
 
    public void setpayload(){
    
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder()
            .add("currentTime", this.currentTime);
            
        payload = objectBuilder.build();
        
    }

    public JsonObject getPayload() {
        return payload;
    }
    

}
