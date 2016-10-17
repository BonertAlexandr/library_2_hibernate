/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.training.web.validators;

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

@FacesValidator("ua.training.web.validators.PasswordValidator")
public class PasswordValidator implements Validator{

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String password = value.toString().trim();
        
        ResourceBundle bundle = ResourceBundle.getBundle("ua.training.web.nls.messages", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        try {
            if(password.length() < 5 || password.length() >= 20)
                throw new IllegalArgumentException(bundle.getString("password_length_error"));            
        } catch(IllegalArgumentException e) {
            FacesMessage message = new FacesMessage(e.getMessage());
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }                
    }
    
}
