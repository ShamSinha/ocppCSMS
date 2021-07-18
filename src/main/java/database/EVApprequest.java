/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;


/**
 *
 * @author Shubham
 */
public class EVApprequest {
    private static Statement stmt;
    private static ResultSet results;
	
    public void addReservation(Reservation reserveObj){
        
        int customerid = reserveObj.getCustomerid();
        Timestamp expirydatetime = reserveObj.getExpirydatetime();
        int chargingstationid = reserveObj.getChargingstationid() ;
        int evseid = reserveObj.getEvseid() ;
        
        String s = "( DEFAULT ," + customerid+"," + expirydatetime + "," + chargingstationid + "," + evseid + ")" ;
        
        String sql_insert = "INSERT INTO reservation VALUES" + s;
		
        try(Connection conn = DBConnection.createNewDBconnection("chargingstation")){

            stmt = conn.createStatement();
            stmt.executeUpdate(sql_insert);

        } catch (SQLException e) {
        }  
    }
    
    public void deleteReservation(int reservationid){
        
        int s = reservationid ;
        String sql_delete = "DELETE FROM reservation WHERE reservationid =" + s ;
        
        try(Connection conn = DBConnection.createNewDBconnection("chargingstation")){

            stmt = conn.createStatement();
            stmt.executeUpdate(sql_delete);

        } catch (SQLException e) {
        }  
    }
    //public List<ChargingStation> getChargingStation(){
        
        
    //}
    
    public void SignUpThroughApp(EVSEUser user){
        String name = user.getName();
  
        String username = user.getUsername();
        int pin = user.getPin();
        
        String s = "(DEFAULT ," + name + "," + username + "," + pin + ")";
        
        String sql_insert_user = "INSERT INTO customer_info VALUES" + s ;
        
        try(Connection conn = DBConnection.createNewDBconnection("customer")){

            stmt = conn.createStatement();
            stmt.executeUpdate(sql_insert_user);

        } catch (SQLException e) {
        }  
    }
    
    public boolean LogInThroughApp(String username, int pin){
        
        String sql_find_user = "SELECT * FROM customer_info WHERE username = " + username + "AND pin = " + pin ;
                
        try(Connection conn = DBConnection.createNewDBconnection("customer")){

            stmt = conn.createStatement();
            results = stmt.executeQuery(sql_find_user);
            
            if(results.next()){
                return true;
            }

        } catch (SQLException e) {
        }  
        return false;
    }
    
}
