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
public class AccountErrorObject implements Serializable {
    private String errorServlet, errorDetail;
    private String invalidEmail, invalidPhone, invalidPassword, invalidConfirmPassword, invalidFullname, invalidAddress;  
    private boolean validateStatus;

    public AccountErrorObject() {
    }

    public String getErrorServlet() {
        return errorServlet;
    }

    public void setErrorServlet(String errorServlet) {
        this.errorServlet = errorServlet;
    }

    public String getErrorDetail() {
        return errorDetail;
    }

    public void setErrorDetail(String errorDetail) {
        this.errorDetail = errorDetail;
    }

    public boolean isValidateStatus() {
        return validateStatus;
    }

    public void setValidateStatus(boolean validateStatus) {
        this.validateStatus = validateStatus;
    }

    public String getInvalidEmail() {
        return invalidEmail;
    }

    public void setInvalidEmail(String invalidEmail) {
        this.invalidEmail = invalidEmail;
    }

    public String getInvalidPhone() {
        return invalidPhone;
    }

    public void setInvalidPhone(String invalidPhone) {
        this.invalidPhone = invalidPhone;
    }

    public String getInvalidPassword() {
        return invalidPassword;
    }

    public void setInvalidPassword(String invalidPassword) {
        this.invalidPassword = invalidPassword;
    }

    public String getInvalidConfirmPassword() {
        return invalidConfirmPassword;
    }

    public void setInvalidConfirmPassword(String invalidConfirmPassword) {
        this.invalidConfirmPassword = invalidConfirmPassword;
    }

    public String getInvalidFullname() {
        return invalidFullname;
    }

    public void setInvalidFullname(String invalidFullname) {
        this.invalidFullname = invalidFullname;
    }

    public String getInvalidAddress() {
        return invalidAddress;
    }

    public void setInvalidAddress(String invalidAddress) {
        this.invalidAddress = invalidAddress;
    }

    @Override
    public String toString() {
        return "AccountErrorObject{" + "invalidEmail=" + invalidEmail + ", invalidPhone=" + invalidPhone + ", invalidPassword=" + invalidPassword + ", invalidConfirmPassword=" + invalidConfirmPassword + ", invalidFullname=" + invalidFullname + ", invalidAddress=" + invalidAddress + '}';
    }
    
}
