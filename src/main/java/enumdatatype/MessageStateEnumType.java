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
public enum MessageStateEnumType {
    Charging , // Message only to be shown while the Charging Station is charging.
    Faulted  , // Message only to be shown while the Charging Station is in faulted state.
    Idle  , //Message only to be shown while the Charging Station is idle (not charging).
    Unavailable ; // Message only to be shown while the Charging Station is in unavailable state.

}