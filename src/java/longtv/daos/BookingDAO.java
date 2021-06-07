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
import java.sql.Timestamp;
import longtv.utils.DatabaseConnection;

/**
 *
 * @author Admin
 */
public class BookingDAO implements Serializable {
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
    
    public boolean insertOrderHeader(String userID, Timestamp requestTime, String orderID) throws Exception {
        boolean check = false;
        try {
            String sql = "INSERT RequestHeader(OrderID, UserID, RequestTime, Status, isDeleted) VALUES(?,?,?,?,0)";
            conn = DatabaseConnection.makeConnection();
            psm = conn.prepareStatement(sql);
            psm.setString(1, orderID);
            psm.setString(2, userID);
            psm.setTimestamp(3, requestTime);
            psm.setInt(4, 1);
            check = psm.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return check;
    }
    
    public boolean insertOrderDetail(int resourceID, String resourceName, int Quantity, String orderID, String rentTime) throws Exception {
        boolean check = false;
        try {
            String sql = "INSERT RequestDetail(OrderID, ResourceID, ResourceName, Quantity, RentTime) VALUES(?,?,?,?,?)";
            conn = DatabaseConnection.makeConnection();
            psm = conn.prepareStatement(sql);
            psm.setString(1, orderID);
            psm.setInt(2, resourceID);
            psm.setString(3, resourceName);
            psm.setInt(4, Quantity);
            psm.setString(5, rentTime);
            check = psm.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return check;
    }
}
