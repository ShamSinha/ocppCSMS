/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datatype;

import enumdatatype.MessageFormatEnumType;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

/**
 *
 * @author Shubham
 */
public class MessageContentType {
    
    public  MessageFormatEnumType format;
    public  String language  ;
    public  String content ;
    private JsonObject messageContent ;

    
    public void setFormat() {
        this.format = MessageFormatEnumType.UTF8;
    }

    public void setLanguage() {
        this.language = "en-US";
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setp(){
    
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder()
            .add("format", this.format.toString())
            .add("language", this.language)
            .add("content", this.content);
    
        messageContent =  objectBuilder.build() ;
    }

    public JsonObject getp() {
        return messageContent;
    }
}
