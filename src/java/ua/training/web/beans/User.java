/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.training.web.beans;

import java.io.Serializable;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Alexandr
 */

@ManagedBean
@SessionScoped
public class User implements Serializable{
    private String username;
    private String password;

    public User() {
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }        
    
    public void setUsername (String userName) {
        this.username = userName;
    }
    
    public String getUsername () {
        return this.username;
    }
    
    public String login() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpServletRequest request = (HttpServletRequest)externalContext.getRequest();        
        try {            
            request.login(username, password);
            return "books";
        } catch (ServletException ex) {
            ResourceBundle bundle = ResourceBundle.getBundle("ua.training.web.nls.messages", facesContext.getViewRoot().getLocale());            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(bundle.getString("login_error")));
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }                
        return "index";
    }
    
    public String logout() {
//        String result = "/index.xhtml?face-redirect=true";
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest request = (HttpServletRequest)externalContext.getRequest();       
        try {            
            request.logout();
        } catch (ServletException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        externalContext.invalidateSession();
        return "exit";
    }
}
