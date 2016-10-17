/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.training.web.validators;

import java.util.ArrayList;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author Alexandr
 */
@FacesValidator("ua.training.web.validators.UsernameValidator")
public class UsernameValidator implements Validator {
    
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        ResourceBundle bundle = ResourceBundle.getBundle("ua.training.web.nls.messages", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        String username = String.valueOf(value).trim();
        try {                                     
            if (!Character.isLetter(username.charAt(0))){
                throw new IllegalArgumentException(bundle.getString("login_first_letter_error"));
            }
            if (getArrayTest(username)){
                throw new IllegalArgumentException(bundle.getString("login_illegal_name_error"));
            }
            if (username.length() < 5 || username.length() >= 30) {
                throw new IllegalArgumentException(bundle.getString("login_length_error"));
            }
        } catch (IllegalArgumentException e) {
                FacesMessage message = new FacesMessage(e.getMessage());
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);
            }
    }
    
    private boolean getArrayTest(String username) {
        ArrayList<String> list = new ArrayList<String>();
        list.add("username");
        list.add("login");
        
        for(String s : list) {
            if(s.equalsIgnoreCase(username))
                return true;
        }
        
        return false;
    }
    
}
