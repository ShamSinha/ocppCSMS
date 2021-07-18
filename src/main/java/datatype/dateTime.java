/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datatype;

/**
 *
 * @author Shubham
 */

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class dateTime {    
       
    public String dT(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        return df.format(calendar.getTime());
    }

}
