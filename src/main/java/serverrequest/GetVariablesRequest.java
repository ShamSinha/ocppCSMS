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
public class GetVariablesRequest {
    
    private JsonObject payload ;
    private GetVariableDataType getVariableData;

    public void setGetVariableData(GetVariableDataType getVariableData) {
        this.getVariableData = getVariableData;
    }
    
    public void setpayload(){
        
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder()
                .add("getVariableData",getVariableData.getp());
                           
        payload = objectBuilder.build();
               
    }

    public JsonObject getPayload() {
        return payload;
    }
    
    
}
