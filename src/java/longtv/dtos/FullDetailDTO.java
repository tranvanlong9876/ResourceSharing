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
public class FullDetailDTO implements Serializable {
    private List<ItemDetailDTO> listDetail;
    private RequestDTO statusDetail;
    private boolean canAccept;

    public FullDetailDTO() {
    }

    public FullDetailDTO(List<ItemDetailDTO> listDetail, RequestDTO statusDetail) {
        this.listDetail = listDetail;
        this.statusDetail = statusDetail;
    }

    public boolean isCanAccept() {
        return canAccept;
    }

    public void setCanAccept(boolean canAccept) {
        this.canAccept = canAccept;
    }

    public List<ItemDetailDTO> getListDetail() {
        return listDetail;
    }

    public void setListDetail(List<ItemDetailDTO> listDetail) {
        this.listDetail = listDetail;
    }

    public RequestDTO getStatusDetail() {
        return statusDetail;
    }

    public void setStatusDetail(RequestDTO statusDetail) {
        this.statusDetail = statusDetail;
    }
    
    
}
