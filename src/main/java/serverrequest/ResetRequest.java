/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverrequest;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import enumdatatype.ResetEnumType;

/**
 *
 * @author Shubham
 */
public class ResetRequest {
    
    private ResetEnumType type = ResetEnumType.Immediate ;
    private int evseId;
    private JsonObject payload;

    public void setType(ResetEnumType t) {
        type = t;
    }

    public void setEvseId(int id) {
        evseId = id;
    }

    public void setpayload(){
        
       
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder()
                .add("type",type.toString())
                .add("evseId",evseId);
                               
        payload = objectBuilder.build();
        
    }

    public JsonObject getPayload() {
        return payload;
    }
    
    
    
    
}
