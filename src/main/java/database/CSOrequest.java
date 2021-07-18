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
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 *
 * @author Shubham
 */
public class CSOrequest {
    
    private static Statement stmt;
    private static ResultSet results;

    public String getRegisteredCS() {

        String sql_select = "Select * From registered_cs";


        String JSONOutput ="";

        try(Connection conn = DBConnection.createNewDBconnection("chargingstation")){

            stmt = conn.createStatement();
            results = stmt.executeQuery(sql_select);

            List<ChargingStation> RegisteredCSList;			
            RegisteredCSList = new ArrayList<>();


             while (results.next()) {

                    ChargingStation cs = new ChargingStation();
                    cs.setId(results.getInt("id"));
                    cs.setSerialNumber(results.getString("serialNumber"));
                    cs.setModel(results.getString("model"));
                    cs.setVendorName(results.getString("vendorName"));
                    cs.setFirmwareVersion(results.getString("firmwareVersion"));
                    cs.setOffline(results.getBoolean("offline"));
                    cs.setOperationalStatus(OperationalStatusEnumType.valueOf(results.getString("operationalStatus")));
                    cs.setLatitude(results.getDouble("latitude"));
                    cs.setLongitude(results.getDouble("longitude")) ;

                    RegisteredCSList.add(cs);
             }

            ObjectMapper mapper = new ObjectMapper();
            JSONOutput = mapper.writeValueAsString(RegisteredCSList);


        } catch (SQLException | JsonProcessingException e) {

        }

        return JSONOutput ;

    }
    
    public void RegisterNewCS(ChargingStation cs){
        String serialNo = cs.getSerialNumber();
        String model = cs.getModel();
        String vendorName = cs.getVendorName();
        String firmwareVersion = cs.getFirmwareVersion();
        boolean offline = cs.isOffline() ;
        OperationalStatusEnumType opStatus = cs.getOperationalStatus() ;
        double latitude = cs.getLatitude();
        double longitude = cs.getLongitude();
        
        String s = "( DEFAULT , " + serialNo+"," + model+"," + vendorName+"," + firmwareVersion+ "," + offline+"," +opStatus+ "," + latitude + "," + longitude +")" ;
        
        String sql_insert_cs = "INSERT INTO registered_cs VALUES" + s ;
        
        try(Connection conn = DBConnection.createNewDBconnection("chargingstation")){

            stmt = conn.createStatement();
            stmt.executeUpdate(sql_insert_cs);

        } catch (SQLException e) {
        }  
          
    }

    public String getConnectedEVSEUser() {

        String sql_select = "Select * From connected_evse_user";
        String JSONOutput ="" ;

        try(Connection conn = DBConnection.createNewDBconnection("customer")){

            stmt = conn.createStatement();
            results = stmt.executeQuery(sql_select);

            List<ConnectedUser> userList;			
            userList = new ArrayList<>();

             while (results.next()) {

                    ConnectedUser user = new ConnectedUser();

                    user.setChargingStationId(results.getInt("chargingstationid"));
                    user.setCustomerId(results.getInt("customerid"));
                    user.setEVSEId(results.getInt("evseid"));

                    userList.add(user);
             }

            ObjectMapper mapper = new ObjectMapper();
            JSONOutput = mapper.writeValueAsString(userList);



        } catch (SQLException  | JsonProcessingException e) {

        }
        return JSONOutput;
    }


    public String getReservationList() {

        String sql_select = "Select * From reservation";


        String JSONOutput ="";

        try(Connection conn = DBConnection.createNewDBconnection("chargingstation")){

            stmt = conn.createStatement();
            results = stmt.executeQuery(sql_select);

            List<Reservation> ReservationList;			
            ReservationList = new ArrayList<>();


             while (results.next()) {

                    Reservation res = new Reservation() ;

                    res.setId(results.getInt("reservationid"));
                    res.setCustomerid(results.getInt("customerid"));
                    res.setExpirydatetime(results.getTimestamp("expirydatetime"));
                    res.setChargingstationid(results.getInt("chargingstationid"));
                    res.setEvseid(results.getInt("evseid")) ;

                    ReservationList.add(res );

             }

            ObjectMapper mapper = new ObjectMapper();
            JSONOutput = mapper.writeValueAsString(ReservationList);


        } catch (SQLException | JsonProcessingException e) {

        }

        return JSONOutput ;

    }

        
}