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
public class ItemDetailDTO implements Serializable {
    private int resourceID, quantity;
    private String resourceName, rentTime;
    private boolean checkQuantity;

    public ItemDetailDTO() {
    }

    public ItemDetailDTO(int resourceID, int quantity, String resourceName, String rentTime) {
        this.resourceID = resourceID;
        this.quantity = quantity;
        this.resourceName = resourceName;
        this.rentTime = rentTime;
    }

    public boolean isCheckQuantity() {
        return checkQuantity;
    }

    public void setCheckQuantity(boolean checkQuantity) {
        this.checkQuantity = checkQuantity;
    }

    public int getResourceID() {
        return resourceID;
    }

    public void setResourceID(int resourceID) {
        this.resourceID = resourceID;
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
