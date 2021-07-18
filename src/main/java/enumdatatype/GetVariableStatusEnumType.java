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
public enum GetVariableStatusEnumType {
    Accepted , // Variable successfully set.
    Rejected  , // Request is rejected.
    UnknownComponent , // Component is not known.
    UnknownVariable , //Variable is not known.
    NotSupportedAttributeType  , // The AttributeType is not supported.
}
