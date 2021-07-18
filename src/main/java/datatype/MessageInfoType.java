/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datatype;

import enumdatatype.MessageStateEnumType;
import enumdatatype.MessagePriorityEnumType;
import datatype.MessageContentType;
import datatype.ComponentType;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

/**
 *
 * @author Shubham
 */
public class MessageInfoType {
    
    private int id ;
    private MessagePriorityEnumType priority ;
    private MessageStateEnumType state ;
    private String startDateTime ;
    private String endDataTime ;
    private String transactionId ;
    private MessageContentType message ;
    private ComponentType display ;
    private JsonObject messageInfo ;

    public void setId(int id) {
        this.id = id;
    }

    public void setPriority(MessagePriorityEnumType priority) {
        this.priority = priority;
    }

    public void setState(MessageStateEnumType state) {
        this.state = state;
    }

    public void setStartDateTime(String startDateTime) {
        this.startDateTime = startDateTime;
    }

    public void setEndDataTime(String endDataTime) {
        this.endDataTime = endDataTime;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public void setMessage(MessageContentType message) {
        this.message = message;
    }

    public void setDisplay(ComponentType display) {
        this.display = display;
    }

    
    public void setp(){
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder()
            .add("id",this.id)
            .add("priority", this.priority.toString())
            .add("state", this.state.toString())
            .add("startDateTime",this.startDateTime)
            .add("endDateTime",this.endDataTime)
            .add("transactionId", this.transactionId)
            .add("message", this.message.getp())
            .add("display",this.display.getp());
    
        messageInfo =  objectBuilder.build() ;
    }

    
    public JsonObject getp() {
        return messageInfo;
    }


    

}