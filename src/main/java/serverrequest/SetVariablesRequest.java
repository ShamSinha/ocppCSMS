/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverrequest;

import datatype.*;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
/**
 *
 * @author Shubham
 */
public class SetVariablesRequest {
    
    private JsonObject payload;
    public void setpayload(SetVariableDataType setVariable){
        
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder()
                .add("setVariableData",setVariable.getp());
         
        payload = objectBuilder.build();
        
    }

    public JsonObject getPayload() {
        return payload;
    }
}
