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
public class HistoryDetailDTO implements Serializable {
    private int quantity;
    private String resourceName, rentTime;

    public HistoryDetailDTO() {
    }

    public HistoryDetailDTO(int quantity, String resourceName, String rentTime) {
        this.quantity = quantity;
        this.resourceName = resourceName;
        this.rentTime = rentTime;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getRentTime() {
        return rentTime;
    }

    public void setRentTime(String rentTime) {
        this.rentTime = rentTime;
    }
    
    
}
