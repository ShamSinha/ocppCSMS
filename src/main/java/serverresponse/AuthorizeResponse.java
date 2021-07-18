/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverresponse;

/**
 *
 * @author Shubham
 */

import javax.json.JsonObject;
import javax.json.Json ;
import javax.json.JsonObjectBuilder;
import datatype.IdTokenInfoType;

public class AuthorizeResponse {

    private JsonObject payload;
    private IdTokenInfoType idTokenInfo ;

    public void setIdToken(IdTokenInfoType idToken) {
        this.idTokenInfo = idToken;
    }
 
    public void setpayload(){
        
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder()
                .add("idTokenInfo",idTokenInfo.getp());
                               
        payload = objectBuilder.build();
        }

    public JsonObject getPayload() {
        return payload;
    }
    
    
}
