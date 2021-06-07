/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longtv.dtos;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Admin
 */
public class SearchDTO implements Serializable {
    private List<CategoryDTO> listOfCategory;
    private int currentPage, currentCategoryID, totalPage;
    private String rentTime;

    public SearchDTO() {
    }

    public SearchDTO(List<CategoryDTO> listOfCategory, int currentPage, int currentCategoryID, String rentTime, int totalPage) {
        this.listOfCategory = listOfCategory;
        this.currentPage = currentPage;
        this.currentCategoryID = currentCategoryID;
        this.rentTime = rentTime;
        this.totalPage = totalPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<CategoryDTO> getListOfCategory() {
        return listOfCategory;
    }

    public void setListOfCategory(List<CategoryDTO> listOfCategory) {
        this.listOfCategory = listOfCategory;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getCurrentCategoryID() {
        return currentCategoryID;
    }

    public void setCurrentCategoryID(int currentCategoryID) {
        this.currentCategoryID = currentCategoryID;
    }

    public String getRentTime() {
        return rentTime;
    }

    public void setRentTime(String rentTime) {
        this.rentTime = rentTime;
    }
    
    
    
}
