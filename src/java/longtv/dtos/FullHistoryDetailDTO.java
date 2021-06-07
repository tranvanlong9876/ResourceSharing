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
public class FullHistoryDetailDTO implements Serializable {
    private List<HistoryDetailDTO> itemDetail;
    private HistoryHeaderDTO requestStatus;

    public FullHistoryDetailDTO() {
    }
    
    public FullHistoryDetailDTO(List<HistoryDetailDTO> itemDetail, HistoryHeaderDTO requestStatus) {
        this.itemDetail = itemDetail;
        this.requestStatus = requestStatus;
    }

    public List<HistoryDetailDTO> getItemDetail() {
        return itemDetail;
    }

    public void setItemDetail(List<HistoryDetailDTO> itemDetail) {
        this.itemDetail = itemDetail;
    }

    public HistoryHeaderDTO getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(HistoryHeaderDTO requestStatus) {
        this.requestStatus = requestStatus;
    }
    
}
