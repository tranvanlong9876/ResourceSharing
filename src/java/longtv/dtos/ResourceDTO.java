/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longtv.dtos;

/**
 *
 * @author Admin
 */
public class ResourceDTO {
    private String itemName, itemColor, categoryName;
    private int itemID, itemQuantity;
    

    public ResourceDTO() {
    }

    public ResourceDTO(String itemName, String itemColor, int itemID, String categoryName, int itemQuantity) {
        this.itemName = itemName;
        this.itemColor = itemColor;
        this.itemID = itemID;
        this.categoryName = categoryName;
        this.itemQuantity = itemQuantity;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemColor() {
        return itemColor;
    }

    public void setItemColor(String itemColor) {
        this.itemColor = itemColor;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }
    
}
