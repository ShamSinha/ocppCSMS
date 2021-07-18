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
public class SetVariableDataType {
    private AttributeEnumType attributeType ;
    private String attributeValue ;
    private ComponentType component ;
    private VariableType variable ;
    private JsonObject setVariablePayload ;

    public void setAttributeType(AttributeEnumType attributeType) {
        this.attributeType = attributeType;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }
    
    public void setComponent(ComponentType component) {
        this.component = component;
    }

    public void setVariable(VariableType variable) {
        this.variable = variable;
    }
    
    public JsonObject getp(){
        return setVariablePayload ;
    }
    
    
    public void setp(){
        
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder()
            .add("attributeType", this.attributeType.toString())
            .add("attributeValue", this.attributeValue)
            .add("component",this.component.getp())
            .add("variable",this.variable.getp());
            
        setVariablePayload =  objectBuilder.build() ;
    } 

    
}

