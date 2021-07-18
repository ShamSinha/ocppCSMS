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
public enum MessagePriorityEnumType {
    AlwaysFront , //Show this message always in front. Highest priority, don’t cycle with other messages. When a newer
    // message with this MessagePriority is received, this message is replaced. No Charging Station own message
    //may override this message.
    InFront , //Show this message in front of the normal cycle of messages. When more messages with this priority are to be shown, they SHALL be cycled.
    NormalCycle ;//Show this message in the cycle of messages.
}
