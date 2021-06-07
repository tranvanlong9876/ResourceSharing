/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longtv.dtos;

import java.io.Serializable;

/**
 *
 * @author Admin
 */
public class HistoryHeaderDTO implements Serializable {
    private String orderID, requestTime, status;

    public HistoryHeaderDTO() {
    }
    
    

    public HistoryHeaderDTO(String orderID, String requestTime, String status) {
        this.orderID = orderID;
        this.requestTime = requestTime;
        this.status = status;
    }
    
    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(String requestTime) {
        this.requestTime = requestTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    
}
