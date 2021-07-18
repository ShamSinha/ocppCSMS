/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datatype;

import enumdatatype.*;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
/**
 *
 * @author Shubham
 */
public class GetVariableDataType {
    
    private AttributeEnumType attributeType ;
    private ComponentType component ;
    private VariableType variable ;
    private JsonObject getVariableDataPayload ;
    

    public void setAttributeType(AttributeEnumType attributeType) {
        this.attributeType = attributeType;
    }

    public void setComponent(ComponentType component) {
        this.component = component;
    }

    public void setVariable(VariableType variable) {
        this.variable = variable;
    }

    public JsonObject getp(){
        return getVariableDataPayload ;
    }

    public void setp(){
        
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder()
            .add("attributeType", this.attributeType.toString() )
            .add("component",component.getp())
            .add("variable", variable.getp());
            
        getVariableDataPayload = objectBuilder.build() ;
    } 
}
