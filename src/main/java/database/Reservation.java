/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.Timestamp;

/**
 *
 * @author Shubham
 */
public class Reservation {
    
    private int id;
    private int customerid;
    private Timestamp expirydatetime ;
    private int evseid ;
    private int chargingstationid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerid() {
        return customerid;
    }

    public void setCustomerid(int customerid) {
        this.customerid = customerid;
    }

    public Timestamp getExpirydatetime() {
        return expirydatetime;
    }

    public void setExpirydatetime(Timestamp expirydatetime) {
        this.expirydatetime = expirydatetime;
    }


    public int getEvseid() {
        return evseid;
    }

    public void setEvseid(int evseid) {
        this.evseid = evseid;
    }

    public int getChargingstationid() {
        return chargingstationid;
    }

    public void setChargingstationid(int chargingstationid) {
        this.chargingstationid = chargingstationid;
    }
    
    
    
    
}
