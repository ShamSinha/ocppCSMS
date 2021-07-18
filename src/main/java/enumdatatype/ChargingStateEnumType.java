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
public enum ChargingStateEnumType {
    
    Charging , // The contactor of the Connector is closed and energy is flowing to between EVSE and EV.
    EVConnected , // There is a connection between EV and EVSE, in case the protocol used between EV and the Charging Station can detect a connection, the protocol needs to detect this for the state to become active. The connection can either be wired or wireless.
    SuspendedEV, // When the EV is connected to the EVSE and the EVSE is offering energy but the EV is not taking any energy.
    SuspendedEVSE , //  When the EV is connected to the EVSE but the EVSE is not offering energy to the EV, e.g. due to a smart charging restriction, local supply power constraints, or when charging has stopped because of the authorization status in the response to a transactionEventRequest indicating that charging is not allowed etc.
    Idle ; // There is no connection between EV and EVSE.
}
