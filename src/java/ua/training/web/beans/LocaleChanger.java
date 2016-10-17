/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.training.web.beans;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpSession;
import ua.training.web.controllers.BookListController;
import ua.training.web.enums.SearchType;

/**
 *
 * @author Alexandr
 */
@ManagedBean(eager = true)
@SessionScoped
public class LocaleChanger implements Serializable {
    private String localeCode = "ru";
    private static Map<String, Object> localesMap;
    
    static{
            localesMap = new LinkedHashMap<String,Object>();            
            localesMap.put("ru", new Locale("ru"));
            localesMap.put("en", Locale.ENGLISH);
    }

    public Map<String, Object> getLocalesMap() {
        return localesMap;
    }
    
    
    
    public void setLocaleCode(String localeCode) {
        this.localeCode = localeCode;
    }

    public String getLocaleCode() {
        return localeCode;
    }
    
    public void localeChangedListner(ValueChangeEvent event) {
        String newLocaleValue = event.getNewValue().toString();
        
        for(Map.Entry<String, Object> entry : localesMap.entrySet()) {
            if(entry.getValue().toString().equals(newLocaleValue)){
                localeCode = newLocaleValue;
                FacesContext.getCurrentInstance().getViewRoot().setLocale((Locale)entry.getValue());
            }
        }        
    }
}
