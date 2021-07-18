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
public enum ReportBaseEnumType {
    ConfigurationInventory, //Required. A (configuration) report that lists all Components/Variables that can be set by the operator.
    FullInventory ,//Required. A (full) report that lists everything except monitoring settings.
    SummaryInventory

}
