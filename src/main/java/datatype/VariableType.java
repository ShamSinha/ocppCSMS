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
public class VariableType {
    
    public String name ;
    private JsonObject variablePayload ;
    

    
    public void setName(String name) {
        this.name = name;
    }
    
    public JsonObject getp(){
        return variablePayload;
    }
 
    public void setp(){
        
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder()
            .add("name", this.name);
               
        variablePayload= objectBuilder.build() ;
    } 
}
