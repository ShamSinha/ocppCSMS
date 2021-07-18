/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverrequest;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

/**
 *
 * @author Shubham
 */
public class CostUpdateRequest {
    
    private JsonObject payload ;
    private float totalCost;
    private String transactionId ;
    
    
    public void settotalCost(float energyimport, float tariff){
        totalCost = energyimport*tariff ;
    }
   
    public void settransactionId(String t){
        transactionId = t ;
    }

    public void setpayload(){
        
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder()
                .add("totalCost", totalCost)
                .add("transactionId", transactionId);               
                             
        payload = objectBuilder.build();
    }

    public JsonObject getPayload() {
        return payload;
    }
    
    
}
