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
public class RequestFillDTO implements Serializable {
    private int currentPage, currentStatusID, totalPage;

    public RequestFillDTO() {
    }

    public RequestFillDTO(int currentPage, int currentStatusID, int totalPage) {
        this.currentPage = currentPage;
        this.currentStatusID = currentStatusID;
        this.totalPage = totalPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getCurrentStatusID() {
        return currentStatusID;
    }

    public void setCurrentStatusID(int currentStatusID) {
        this.currentStatusID = currentStatusID;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
}
