/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datatype;

import enumdatatype.AuthorizationStatusEnumType;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

/**
 *
 * @author Shubham
 */
public class IdTokenInfoType {
    
    private AuthorizationStatusEnumType status = AuthorizationStatusEnumType.Invalid;
    private String cacheExpiryDateTime ;
    private int chargingPriority ;
    private MessageContentType personalMessage ;
    private JsonObject idTokenInfo ;
    
   
    public void setCacheExpiryDateTime(int HowMuchHour) {  
        expirydateTime e = new expirydateTime();
        this.cacheExpiryDateTime = e.dT(HowMuchHour);
    }
    
    public void setStatus(AuthorizationStatusEnumType status) {
        this.status = status;
    }

    public void setChargingPriority(int chargingPriority) {
        this.chargingPriority = chargingPriority;
    }

    public void setPersonalMessage(MessageContentType personalMessage) {
        this.personalMessage = personalMessage;
    }
 

    public JsonObject getp(){
        return idTokenInfo ;
    }
    
    public void setp(){
        
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder()
            .add("status", this.status.toString())
            .add("cacheExpiryDateTime",this.cacheExpiryDateTime)
            .add("getChargingPriority", this.chargingPriority)
            .add("personalMessage", this.personalMessage.getp());
    
        idTokenInfo =  objectBuilder.build() ;
    }       
}
