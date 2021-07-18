/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datatype;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

/**
 *
 * @author Shubham
 */
public class EVSEType {
    
    private int id = 0;  // This contains a number (> 0) designating an EVSE of the Charging Station
    private int connectorId  = 0; // An id to designate a specific connector (on an EVSE) by connector index number.
    private JsonObject evsePayload ;

    public void setId(int id) {
        this.id = id;
    }

    public void setConnectorId(int connectorId) {
        this.connectorId = connectorId;
    }
    
    public JsonObject getp(){
        return evsePayload ;
    }
    
    public void setp(){
          
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder()
            .add("id", this.id)
            .add("connectorId", this.connectorId);
    
        evsePayload = objectBuilder.build() ;
    }
}
