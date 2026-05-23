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
    private Integer chargingPriority ;
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
        if (idTokenInfo == null) {
            setp();
        }
        return idTokenInfo ;
    }

    public void setp(){

        JsonObjectBuilder objectBuilder = Json.createObjectBuilder()
            .add("status", this.status.toString());

        if (this.cacheExpiryDateTime != null) {
            objectBuilder.add("cacheExpiryDateTime", this.cacheExpiryDateTime);
        }
        if (this.chargingPriority != null) {
            objectBuilder.add("chargingPriority", this.chargingPriority);
        }
        if (this.personalMessage != null && this.personalMessage.getp() != null) {
            objectBuilder.add("personalMessage", this.personalMessage.getp());
        }

        idTokenInfo =  objectBuilder.build() ;
    }
}
