/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverrequest;
import enumdatatype.* ;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
/**
 *
 * @author Shubham
 */
public class GetBaseReportRequest {
    
    private JsonObject payload;
    private int requestId ;
    private ReportBaseEnumType reportBase ;

    public void setRequestId(int requestId) {
        requestId = requestId;
    }

    public void setReportBase(ReportBaseEnumType reportBase) {
        reportBase = reportBase;
    }

    public void setpayload(){
        
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder()
                .add("requestId", requestId)
                .add("reportBase" ,reportBase.toString());
                        
        payload = objectBuilder.build();
        
        
    }

    public JsonObject getPayload() {
        return payload;
    }
}
