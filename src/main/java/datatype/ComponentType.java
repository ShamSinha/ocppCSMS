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
public class ComponentType {
    
    private String name ;  
    private JsonObject componentPayload ;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public JsonObject getp(){
        return componentPayload;
    }
    
    public void setp(EVSEType evsetype){
        
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder()
            .add("name", name)
            .add("evse", evsetype.getp());                                  
            
        componentPayload = objectBuilder.build() ;
    } 


}
