/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

/**
 *
 * @author Shubham
 */
public class ConnectedUser {
    
    private int customerId;
    private int EVSEId ;
    private int chargingStationId ;

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getEVSEId() {
        return EVSEId;
    }

    public void setEVSEId(int EVSEId) {
        this.EVSEId = EVSEId;
    }
    
    public int getChargingStationId() {
        return chargingStationId;
    }

    public void setChargingStationId(int chargingStationId) {
        this.chargingStationId = chargingStationId;
    }
    

 
}
