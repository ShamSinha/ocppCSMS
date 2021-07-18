/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverresponse;



import javax.json.JsonObject;
import javax.json.Json;
import javax.json.JsonObjectBuilder;
import enumdatatype.RegistrationStatusEnumType;
import datatype.dateTime;
/**
 *
 * @author Shubham
 */
public class BootNotificationResponse {
    private int interval ;       // in seconds   this decide the frequency of incoming HeartbeatRequest
    private String currentTime ;
    private RegistrationStatusEnumType status ;
    private JsonObject payload ;

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public void setStatus(RegistrationStatusEnumType status) {
        this.status = status;
    }

    public void setCurrentTime() {
          dateTime d = new dateTime() ;
          this.currentTime = d.dT();
    }      

    public void setpayload(){
    
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder()
            .add("currentTime", this.currentTime)
            .add("interval", this.interval)
            .add("status", this.status.toString() );
    
        payload = objectBuilder.build();
    }
    
    public JsonObject getpayload(){
        return this.payload ;
    }

}
