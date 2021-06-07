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
import longtv.dtos.HistoryDetailDTO;
import longtv.dtos.HistoryHeaderDTO;
import longtv.utils.DatabaseConnection;

/**
 *
 * @author Admin
 */
public class HistoryDAO implements Serializable {
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
    
    public List<HistoryHeaderDTO> loadHistoryHeader(String userID, String date) throws Exception {
        List<HistoryHeaderDTO> result = new ArrayList<>();
        HistoryHeaderDTO dto;
        try {
            String sql = "SELECT OrderID, UserID, CONVERT(nvarchar, RequestTime, 120) AS RequestTime, StatusName\n" +
                         "FROM RequestHeader H JOIN BookingStatus B\n" +
                         "ON H.Status = B.StatusID\n" +
                         "WHERE UserID = ? AND Convert(nvarchar, RequestTime, 120) LIKE ? AND isDeleted = 0\n" + 
                         "ORDER BY RequestTime DESC";
            conn = DatabaseConnection.makeConnection();
            psm = conn.prepareStatement(sql);
            psm.setString(1, userID);
            psm.setString(2, "%" + date + "%");
            rs = psm.executeQuery();
            while(rs.next()) {
                String orderID = rs.getString("OrderID");
                String requestTime = rs.getString("RequestTime");
                String statusName = rs.getString("StatusName");
                if(rs.getString("StatusName").equals("Accept")) {
                    statusName = "Approved";
                } else if(rs.getString("StatusName").equals("Delete")){
                    statusName = "Denied";
                }
                dto = new HistoryHeaderDTO(orderID, requestTime, statusName);
                result.add(dto);
            }
        } finally {
            closeConnection();
        }
        return result;
    } 
    
    public List<HistoryHeaderDTO> loadHistoryHeaderBetweenDate(String userID, String dateFrom, String dateTo) throws Exception {
        List<HistoryHeaderDTO> result = new ArrayList<>();
        HistoryHeaderDTO dto;
        try {
            String sql = "SELECT OrderID, UserID, CONVERT(nvarchar, RequestTime, 120) AS RequestTime, StatusName \n" +
                         "FROM RequestHeader H JOIN BookingStatus B\n" +
                         "ON H.Status = B.StatusID\n" +
                         "WHERE UserID = ? AND (Convert(nvarchar, RequestTime, 23) BETWEEN ? AND ?) AND isDeleted = 0\n"
                       + "ORDER BY RequestTime DESC";
            conn = DatabaseConnection.makeConnection();
            psm = conn.prepareStatement(sql);
            psm.setString(1, userID);
            psm.setString(2, dateFrom);
            psm.setString(3, dateTo);
            rs = psm.executeQuery();
            while(rs.next()) {
                String orderID = rs.getString("OrderID");
                String requestTime = rs.getString("RequestTime");
                String statusName = rs.getString("StatusName");
                if(rs.getString("StatusName").equals("Accept")) {
                    statusName = "Approved";
                } else if(rs.getString("StatusName").equals("Delete")){
                    statusName = "Denied";
                }
                dto = new HistoryHeaderDTO(orderID, requestTime, statusName);
                result.add(dto);
            }
        } finally {
            closeConnection();
        }
        return result;
    }
    
    public List<HistoryDetailDTO> loadHistoryDetail(String userID, String orderID) throws Exception {
        List<HistoryDetailDTO> result = new ArrayList<>();
        HistoryDetailDTO dto;
        try {
            String sql = "SELECT ResourceName, Quantity, RentTime\n" +
                         "FROM RequestHeader H JOIN RequestDetail D\n" +
                         "ON H.OrderID = D.OrderID\n" +
                         "WHERE H.UserID = ? AND H.OrderID = ? AND isDeleted = 0";
            conn = DatabaseConnection.makeConnection();
            psm = conn.prepareStatement(sql);
            psm.setString(1, userID);
            psm.setString(2, orderID);
            rs = psm.executeQuery();
            while(rs.next()) {
                String resourceName = rs.getString("ResourceName");
                String rentTime = rs.getString("RentTime");
                int resourceQuantity = rs.getInt("Quantity");
                dto = new HistoryDetailDTO(resourceQuantity, resourceName, rentTime);
                result.add(dto);
            }
        } finally {
            closeConnection();
        }
        return result;
    }
    
    public HistoryHeaderDTO loadHistoryHeaderByOrderID(String userID, String orderID) throws Exception {
        HistoryHeaderDTO dto = null;
        try {
            String sql = "SELECT CONVERT(nvarchar,RequestTime,120) AS RequestTime, B.StatusName\n" +
                         "FROM RequestHeader H JOIN BookingStatus B\n" +
                         "ON H.Status = B.StatusID\n" +
                         "WHERE UserID = ? AND OrderID = ? AND isDeleted = 0";
            conn = DatabaseConnection.makeConnection();
            psm = conn.prepareStatement(sql);
            psm.setString(1, userID);
            psm.setString(2, orderID);
            rs = psm.executeQuery();
            if(rs.next()) {
                String requestTime = rs.getString("RequestTime");
                String statusName = rs.getString("StatusName");
                if(rs.getString("StatusName").equals("Accept")) {
                    statusName = "Approved";
                } else if(rs.getString("StatusName").equals("Delete")){
                    statusName = "Denied";
                }
                dto = new HistoryHeaderDTO();
                dto.setRequestTime(requestTime);
                dto.setStatus(statusName);
            }
        } finally {
            closeConnection();
        }
        return dto;
    }
    
    public boolean deleteHistoryHeader(String userID, String orderID) throws Exception {
        boolean check = false;
        try {
            String sql = "UPDATE RequestHeader SET isDeleted = 1 "
                       + "WHERE userID = ? AND OrderID = ?";
            conn = DatabaseConnection.makeConnection();
            psm = conn.prepareStatement(sql);
            psm.setString(1, userID);
            psm.setString(2, orderID);
            check = psm.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return check;
    }
}
