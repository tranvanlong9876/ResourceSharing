/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longtv.dtos;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Admin
 */
public class CartDTO implements Serializable {
    private String userID;
    private Map<Integer, BookDTO> cart;

    public CartDTO() {
        cart = new HashMap<>();
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Map<Integer, BookDTO> getCart() {
        return cart;
    }

    public void setCart(Map<Integer, BookDTO> cart) {
        this.cart = cart;
    }
    
    public void addToCart(BookDTO dto) throws Exception {
        this.cart.put(dto.getItemID(), dto);
    }
    
    public void deleteCart(int id) throws Exception {
        if(this.cart.containsKey(id)) {
            this.cart.remove(id);
        }
    }
}
