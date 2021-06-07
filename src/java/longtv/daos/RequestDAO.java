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
import longtv.dtos.ItemDetailDTO;
import longtv.dtos.RequestDTO;
import longtv.dtos.ResourceDTO;
import longtv.utils.DatabaseConnection;

/**
 *
 * @author Admin
 */
public class RequestDAO implements Serializable {
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
    
    public List<RequestDTO> loadRequestByStatusNew(int status, int currentPage, String search) throws Exception {
        List<RequestDTO> result = new ArrayList<>();
        RequestDTO dto;
        try {
            String sql = "SELECT OrderID, UserID, CONVERT(nvarchar, RequestTime, 120) AS RequestTime, B.StatusName\n" +
                         "FROM RequestHeader R JOIN BookingStatus B\n" +
                         "ON R.Status = B.StatusID\n" +
                         "WHERE R.Status = ? AND UserID LIKE ? AND R.isDeleted = 0\n" +
                         "ORDER BY R.RequestTime DESC\n" +
                         "OFFSET ? ROWS\n" +
                         "FETCH NEXT 20 ROWS ONLY";
            conn = DatabaseConnection.makeConnection();
            psm = conn.prepareStatement(sql);
            psm.setInt(1, status);
            psm.setString(2, "%" + search + "%");
            psm.setInt(3, (currentPage - 1) * 20);
            rs = psm.executeQuery();
            while(rs.next()) {
                String orderID = rs.getString("OrderID");
                String date = rs.getString("RequestTime");
                String userID = rs.getString("UserID");
                String statusName = rs.getString("StatusName");
                dto = new RequestDTO(userID, orderID, statusName, date);
                result.add(dto);
            }
        } finally {
            closeConnection();
        }
        return result;
    }
    
    public List<RequestDTO> loadRequestByStatusNotNew(int status, int currentPage, String search) throws Exception {
        List<RequestDTO> result = new ArrayList<>();
        RequestDTO dto;
        try {
            String sql = "SELECT OrderID, UserID, CONVERT(nvarchar, RequestTime, 120) AS RequestTime, B.StatusName\n" +
                         "FROM RequestHeader R JOIN BookingStatus B\n" +
                         "ON R.Status = B.StatusID\n" +
                         "WHERE R.Status = ? AND UserID LIKE ?\n" +
                         "ORDER BY R.RequestTime DESC\n" +
                         "OFFSET ? ROWS\n" +
                         "FETCH NEXT 20 ROWS ONLY";
            conn = DatabaseConnection.makeConnection();
            psm = conn.prepareStatement(sql);
            psm.setInt(1, status);
            psm.setString(2, "%" + search + "%");
            psm.setInt(3, (currentPage - 1) * 20);
            rs = psm.executeQuery();
            while(rs.next()) {
                String orderID = rs.getString("OrderID");
                String date = rs.getString("RequestTime");
                String userID = rs.getString("UserID");
                String statusName = rs.getString("StatusName");
                dto = new RequestDTO(userID, orderID, statusName, date);
                result.add(dto);
            }
        } finally {
            closeConnection();
        }
        return result;
    }
    
    public int loadAllRowsNotNew(int status, String search) throws Exception {
        int rows = 0;
        try {
            String sql = "SELECT COUNT(OrderID) AS TotalRows\n" +
                         "FROM RequestHeader R JOIN BookingStatus B\n" +
                         "ON R.Status = B.StatusID\n" +
                         "WHERE R.Status = ? AND UserID LIKE ?";
            conn = DatabaseConnection.makeConnection();
            psm = conn.prepareStatement(sql);
            psm.setInt(1, status);
            psm.setString(2, "%" + search + "%");
            rs = psm.executeQuery();
            if(rs.next()) {
                rows = rs.getInt("TotalRows");
            }
        } finally {
            closeConnection();
        }
        return rows;
    }
    
    public int loadAllRowsNew(int status, String search) throws Exception {
        int rows = 0;
        try {
            String sql = "SELECT COUNT(OrderID) AS TotalRows\n" +
                         "FROM RequestHeader R JOIN BookingStatus B\n" +
                         "ON R.Status = B.StatusID\n" +
                         "WHERE R.Status = ? AND UserID LIKE ? AND R.isDeleted = 0";
            conn = DatabaseConnection.makeConnection();
            psm = conn.prepareStatement(sql);
            psm.setInt(1, status);
            psm.setString(2, "%" + search + "%");
            rs = psm.executeQuery();
            if(rs.next()) {
                rows = rs.getInt("TotalRows");
            }
        } finally {
            closeConnection();
        }
        return rows;
    }
    
    public List<ItemDetailDTO> loadRequestDetailByID(String orderID) throws Exception {
        List<ItemDetailDTO> result = new ArrayList<>();
        ItemDetailDTO dto;
        try {
            String sql = "SELECT ResourceID, ResourceName, Quantity, RentTime\n" +
                         "FROM RequestDetail\n" +
                         "WHERE OrderID LIKE ?";
            conn = DatabaseConnection.makeConnection();
            psm = conn.prepareStatement(sql);
            psm.setString(1, "%" + orderID + "%");
            rs = psm.executeQuery();
            while(rs.next()) {
                int itemID = rs.getInt("ResourceID");
                String rentDate = rs.getString("RentTime");
                String itemName = rs.getString("ResourceName");
                int quantity = rs.getInt("Quantity");
                dto = new ItemDetailDTO(itemID, quantity, itemName, rentDate);
                result.add(dto);
            }
        } finally {
            closeConnection();
        }
        return result;
    }
    
    public RequestDTO loadRequestHeaderByID(String orderID) throws Exception {
        RequestDTO dto = null;
        try {
            String sql = "SELECT UserID, StatusName\n" +
                         "FROM RequestHeader H JOIN BookingStatus B\n" +
                         "ON H.Status = B.StatusID\n" +
                         "WHERE OrderID LIKE ?";
            conn = DatabaseConnection.makeConnection();
            psm = conn.prepareStatement(sql);
            psm.setString(1, "%" + orderID + "%");
            rs = psm.executeQuery();
            if(rs.next()) {
                String userID = rs.getString("UserID");
                String statusName = rs.getString("StatusName");
                dto = new RequestDTO();
                dto.setUserID(userID);
                dto.setStatusName(statusName);
            }
        } finally {
            closeConnection();
        }
        return dto;
    }
    
    public int getResourceBasedOnID(int resourceID) throws Exception {
        int quantity = 0;
        try {
            String sql = "SELECT itemQuantity\n" +
                         "FROM ItemList\n" +
                         "WHERE itemID = ?";
            conn = DatabaseConnection.makeConnection();
            psm = conn.prepareStatement(sql);
            psm.setInt(1, resourceID);
            rs = psm.executeQuery();
            if(rs.next()) {
                quantity = rs.getInt("itemQuantity");
            }
        } finally {
            closeConnection();
        }
        return quantity;
    }
    
    public int getResourceCurrentlyOnUse(int resourceID, String rentTime) throws Exception {
        int quantity = 0;
        try {
            String sql = "SELECT Quantity\n" +
                         "FROM RequestDetail D JOIN RequestHeader H\n" +
                         "ON D.OrderID = H.OrderID\n" +
                         "WHERE H.Status = 2 AND ResourceID = ? AND RentTime = ?";
            conn = DatabaseConnection.makeConnection();
            psm = conn.prepareStatement(sql);
            psm.setInt(1, resourceID);
            psm.setString(2, rentTime);
            rs = psm.executeQuery();
            while(rs.next()) {
                quantity += rs.getInt("Quantity");
            }
        } finally {
            closeConnection();
        }
        return quantity;
    }
    
    public boolean acceptRequest(String orderID) throws Exception {
        boolean check = false;
        try {
            String sql = "UPDATE RequestHeader SET Status = 2\n"
                       + "WHERE OrderID = ?";
            conn = DatabaseConnection.makeConnection();
            psm = conn.prepareStatement(sql);
            psm.setString(1, orderID);
            check = psm.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return check;
    }
    
    public boolean deleteRequest(String orderID) throws Exception {
        boolean check = false;
        try {
            String sql = "UPDATE RequestHeader SET Status = 3\n"
                       + "WHERE OrderID = ?";
            conn = DatabaseConnection.makeConnection();
            psm = conn.prepareStatement(sql);
            psm.setString(1, orderID);
            check = psm.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return check;
    }
}
