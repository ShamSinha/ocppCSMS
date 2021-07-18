/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enumdatatype;

/**
 *
 * @author Shubham
 */
public enum RegistrationStatusEnumType {
    Accepted , //Charging Station is accepted by the CSMS.
    Pending , //CSMS is not yet ready to accept the Charging Station. CSMS may send messages to retrieve information or prepare the Charging Station.
    Rejected , //Charging Station is not accepted by CSMS. This may happen when the Charging Station id is not known by CSMS. 
}
