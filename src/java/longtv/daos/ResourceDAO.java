/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longtv.daos;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import longtv.dtos.CategoryDTO;
import longtv.dtos.ResourceDTO;
import longtv.utils.DatabaseConnection;

/**
 *
 * @author Admin
 */
public class ResourceDAO implements Serializable {
    Connection conn;
    PreparedStatement psm;
    ResultSet rs;
    
    private void closeConnection() throws Exception {
        if(rs != null) {
            rs.close();
        }
        if(psm != null) {
            psm.close();
        }
        if(conn != null) {
            conn.close();
        }
    }
    
    public List<CategoryDTO> loadAllCategory() throws Exception {
        List<CategoryDTO> category = new ArrayList<>();
        CategoryDTO dto;
        try {
            String sql = "SELECT categoryID, cateName FROM Category";
            conn = DatabaseConnection.makeConnection();
            psm = conn.prepareStatement(sql);
            rs = psm.executeQuery();
            while(rs.next()) {
                int id = rs.getInt("categoryID");
                String name = rs.getString("cateName");
                dto = new CategoryDTO(id, name);
                category.add(dto);
            }
        } finally {
            closeConnection();
        }
        return category;
    }
    
    public List<ResourceDTO> loadAllResource(int ID, String searchName, String time, int page) throws Exception {
        List<ResourceDTO> result = new ArrayList<>();
        ResourceDTO dto;
        try {
            String sql = "SELECT itemID, itemName, itemColor, itemQuantity, C.cateName\n"
                       + "FROM ItemList I JOIN Category C\n"
                       + "ON I.cateID = C.categoryID\n"
                       + "WHERE I.cateID LIKE ? AND itemName LIKE ? AND (? BETWEEN usingDate AND returnDate)\n"
                       + "ORDER BY itemName\n"
                       + "OFFSET ? ROWS\n"
                       + "FETCH NEXT 20 ROWS ONLY";
            conn = DatabaseConnection.makeConnection();
            psm = conn.prepareStatement(sql);
            psm.setString(1, "%" + (ID > 0 ? ID : "") + "%");
            psm.setString(2, "%" + searchName + "%");
            psm.setString(3, time);
            psm.setInt(4, (page - 1) * 20);
            rs = psm.executeQuery();
            while(rs.next()) {
                int itemID = rs.getInt("itemID");
                String itemName = rs.getString("itemName");
                String itemColor = rs.getString("itemColor");
                int itemQuantity = rs.getInt("itemQuantity");
                String categoryName = rs.getString("cateName");
                dto = new ResourceDTO(itemName, itemColor, itemID, categoryName, itemQuantity);
                result.add(dto);
            }
        } finally {
            closeConnection();
        }
        return result;
    }
    
    public int loadAllRows(int ID, String searchName, String time) throws Exception {
        int row = 0;
        try {
            String sql = "SELECT COUNT(*) AS totalItem\n" +
                         "FROM ItemList I JOIN Category C\n" +
                         "ON I.cateID = C.categoryID\n" +
                         "WHERE I.cateID LIKE ? AND itemName LIKE ? AND (? BETWEEN usingDate AND returnDate)";
            conn = DatabaseConnection.makeConnection();
            psm = conn.prepareStatement(sql);
            psm.setString(1, "%" + (ID > 0 ? ID : "") + "%");
            psm.setString(2, "%" + searchName + "%");
            psm.setString(3, time);
            rs = psm.executeQuery();
            if(rs.next()) {
                row = rs.getInt("totalItem");
            }
        } finally {
            closeConnection();
        }
        return row;
    } 
}
