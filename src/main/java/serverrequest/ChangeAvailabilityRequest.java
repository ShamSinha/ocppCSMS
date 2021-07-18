/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverrequest;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import enumdatatype.OperationalStatusEnumType;
import datatype.EVSEType;

/**
 *
 * @author Shubham
 */
public class ChangeAvailabilityRequest {
    
    private JsonObject payload ;
    private EVSEType evse ;
    private OperationalStatusEnumType operationalStatus;
  
    public void setOperationalStatus(OperationalStatusEnumType operationalStatus) {
        this.operationalStatus = operationalStatus;
    }

    public void setEvse(EVSEType evse) {
        this.evse = evse;
    }
    
    public void setpayload(){
        
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder()
                .add("operationalStatus",this.operationalStatus.toString())
                .add("evse",this.evse.getp());
                 
        this.payload = objectBuilder.build();
            
    }
    public JsonObject getPayload() {
        return payload;
    }

    
    
    
    
}
