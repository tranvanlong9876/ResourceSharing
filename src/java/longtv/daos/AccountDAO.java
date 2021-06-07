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
import java.util.Date;
import longtv.dtos.AccountDTO;
import longtv.dtos.RegisterDTO;
import longtv.utils.DatabaseConnection;

/**
 *
 * @author Admin
 */
public class AccountDAO implements Serializable {
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
    
    public AccountDTO checkLogin(String username, String password) throws Exception {
        AccountDTO dto = null;
        try {
            String sql = "SELECT userID, name, role, status FROM Account "
                    + "WHERE userID = ? AND password = ?";
            conn = DatabaseConnection.makeConnection();
            psm = conn.prepareStatement(sql);
            psm.setString(1, username);
            psm.setString(2, password);
            rs = psm.executeQuery();
            if(rs.next()) {
                String id = rs.getString("userID");
                String name = rs.getString("name");
                String role = rs.getString("role");
                String status = rs.getString("status");
                dto = new AccountDTO();
                dto.setUsername(id);
                dto.setName(name);
                dto.setRole(role);
                dto.setStatus(status);
            }
        } finally {
            closeConnection();
        }
        return dto;
    }
    
    public AccountDTO loginWithGoogle(String email) throws Exception {
        AccountDTO dto = null;
        try {
            String sql = "SELECT userID, name, role, status FROM Account " +
                         "WHERE userID = ?";
            conn = DatabaseConnection.makeConnection();
            psm = conn.prepareStatement(sql);
            psm.setString(1, email);
            rs = psm.executeQuery();
            if(rs.next()) {
                String id = rs.getString("userID");
                String name = rs.getString("name");
                String role = rs.getString("role");
                String status = rs.getString("status");
                dto = new AccountDTO();
                dto.setUsername(id);
                dto.setName(name);
                dto.setRole(role);
                dto.setStatus(status);
            }
        } finally {
            closeConnection();
        }
        return dto;
    }
    
    public boolean createAccount(RegisterDTO reDTO) throws Exception {
        boolean isSuccess = false;
        try {
            Date date = new Date();
            Timestamp currentTime = new Timestamp(date.getTime());
            String sql = "INSERT INTO Account(userID, password, name, phone, address, dateOfCreate, status, role) "
                       + "VALUES(?,?,?,?,?,?,?,?)";
            conn = DatabaseConnection.makeConnection();
            psm = conn.prepareStatement(sql);
            psm.setString(1, reDTO.getEmail());
            psm.setString(2, reDTO.getPassword());
            psm.setString(3, reDTO.getFullName());
            psm.setString(4, reDTO.getPhone());
            psm.setString(5, reDTO.getAddress());
            psm.setTimestamp(6, currentTime);
            psm.setString(7, "New");
            psm.setString(8, "Employee");
            isSuccess = psm.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return isSuccess;
    }
    
    public boolean setCodeToVerifyAccount(int code, String email) throws Exception {
        boolean isSuccess = false;
        try {
            Date date = new Date();
            Timestamp currentTime = new Timestamp(date.getTime());
            conn = DatabaseConnection.makeConnection();
            String sql = "";
            AccountDAO dao = new AccountDAO();
            if(dao.checkExistEmailToVerify(email)) {
                sql = "UPDATE AccountVerify SET verifyCode = ?, sendDate = ? WHERE userID = ?";
                psm = conn.prepareStatement(sql);
            } else {
                sql = "INSERT AccountVerify(verifyCode, sendDate, userID) VALUES(?,?,?)";
                psm = conn.prepareStatement(sql);
            }
            
            psm.setInt(1, code);
            psm.setTimestamp(2, currentTime);
            psm.setString(3, email);
            isSuccess = psm.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return isSuccess;
    }
    
    public boolean checkExistEmailToVerify(String email) throws Exception {
        boolean isExisted = false;
        try {
            String sql = "SELECT userID FROM AccountVerify WHERE userID = ?";
            conn = DatabaseConnection.makeConnection();
            psm = conn.prepareStatement(sql);
            psm.setString(1, email);
            rs = psm.executeQuery();
            if(rs.next()) {
                isExisted = true;
            }
        } finally {
            closeConnection();
        }
        return isExisted;
    }
    
    public boolean checkExistEmail(String email) throws Exception {
        boolean isExisted = false;
        try {
            String sql = "SELECT userID FROM Account WHERE userID = ?";
            conn = DatabaseConnection.makeConnection();
            psm = conn.prepareStatement(sql);
            psm.setString(1, email);
            rs = psm.executeQuery();
            if(rs.next()) {
                isExisted = true;
            }
        } finally {
            closeConnection();
        }
        return isExisted;
    }
    
    public boolean checkVerificationCode(String email, int code) throws Exception {
        boolean success = false;
        try {
            String sql = "SELECT userID FROM AccountVerify WHERE userID = ? AND verifyCode = ?";
            conn = DatabaseConnection.makeConnection();
            psm = conn.prepareStatement(sql);
            psm.setString(1, email);
            psm.setInt(2, code);
            rs = psm.executeQuery();
            if(rs.next()) {
                success = true;
            }
        } finally {
            closeConnection();
        }
        return success;
    }
    
    public boolean activeAccount(String email) throws Exception {
        boolean success = false;
        try {
            String sql = "UPDATE Account SET status = ? WHERE userID = ?";
            conn = DatabaseConnection.makeConnection();
            psm = conn.prepareStatement(sql);
            psm.setString(1, "Active");
            psm.setString(2, email);
            success = psm.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return success;
    }
}
