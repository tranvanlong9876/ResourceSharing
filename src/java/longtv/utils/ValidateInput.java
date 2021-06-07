/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longtv.utils;

import java.io.Serializable;
import longtv.dtos.AccountErrorObject;
import longtv.dtos.RegisterDTO;

/**
 *
 * @author Admin
 */
public class ValidateInput implements Serializable {
    public static boolean validateEmail(String email) {
        return email.matches("^[a-zA-Z]+[a-zA-Z0-9\\-_]+@[a-zA-Z]+(\\.[a-zA-Z]+){1,3}$");
    }
    
    public static boolean validatePhone(String phone) {
        return phone.matches("[0-9]{3,15}");
    }
    
    public static boolean validateAddress(String address) {
        return (address.length() <= 200 && address.length() > 0);
    }
    
    public static boolean validatePassword(String password) {
        return (password.length() <= 50 && password.length() > 6);
    }
    
    public static boolean validateMatchingPassword(String password, String confirm) {
        return password.equals(confirm);
    }
    
    public static boolean validateFullName(String fullName) {
        return (fullName.length() >= 2 && fullName.length() <= 50);
    }
    
    public AccountErrorObject statusValidate(RegisterDTO dto) {
        AccountErrorObject errorObject = new AccountErrorObject();
        boolean valid = true;
        if(!validateEmail(dto.getEmail())) {
            errorObject.setInvalidEmail("Your email is invalid");
            valid = false;
        }
        if(!validatePassword(dto.getPassword())) {
            errorObject.setInvalidPassword("Your password is invalid");
            valid = false;
        }
        if(!validateMatchingPassword(dto.getPassword(), dto.getConfirmPassword())) {
            errorObject.setInvalidConfirmPassword("Password must match confirm password");
            valid = false;
        }
        if(!validatePhone(dto.getPhone())) {
            errorObject.setInvalidPhone("Your phone is invalid");
            valid = false;
        }
        if(!validateAddress(dto.getAddress())) {
            errorObject.setInvalidAddress("Your address is invalid");
            valid = false;
        }
        
        if(!validateFullName(dto.getFullName())) {
            errorObject.setInvalidFullname("Name too short or too long");
            valid = false;
        }
        
        errorObject.setValidateStatus(valid);
        return errorObject;
    }

}
