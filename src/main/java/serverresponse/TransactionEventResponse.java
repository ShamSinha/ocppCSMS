/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverresponse;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import datatype.IdTokenInfoType;
import datatype.MessageContentType;

/** 
   
 * @author Shubham
 */
public class TransactionEventResponse {
    
    private float totalCost = 0 ;
    private int chargingPriority = 0 ;
    private IdTokenInfoType idTokenInfo ;
    private MessageContentType messageContent ;
    private JsonObject payload ;
    
    public void setTotalCost(float totalCost) {
        this.totalCost = totalCost;
    }

    public void setChargingPriority(int chargingPriority) {
        this.chargingPriority = chargingPriority;
    }

    public void setIdTokenInfo(IdTokenInfoType idTokenInfo) {
        this.idTokenInfo = idTokenInfo;
    }

    public void setMessageContent(MessageContentType messageContent) {
        this.messageContent = messageContent;
    }
    
    public void setpayload(boolean haveIdToken, boolean haveChargingfinished){

        JsonObjectBuilder objectBuilder = Json.createObjectBuilder() ;
            if(haveChargingfinished){
                objectBuilder.add("totalCost", this.totalCost);
            }
            
            objectBuilder.add("chargingpriority", this.chargingPriority);
            
            if(haveIdToken){
                objectBuilder.add("idTokenInfo", idTokenInfo.getp()) ;
            }   
            
            objectBuilder.add("updatedPersonalMessage" , messageContent.getp()) ;
         
            
        payload = objectBuilder.build();
          
    }
    public JsonObject getPayload(){
        return payload ;
    }

   
}
