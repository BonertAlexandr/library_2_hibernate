/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.training.web.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import ua.training.web.enums.SearchType;

/**
 *
 * @author Alexandr
 */

@ManagedBean
@RequestScoped
public class SearchTypeController {
    private Map<String, SearchType> searchTypeList = new HashMap<String, SearchType>();

    public SearchTypeController() {
        ResourceBundle bundle = ResourceBundle.getBundle("ua.training.web.nls.messages", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        searchTypeList.put(bundle.getString("author_name"), SearchType.AUTHOR);
        searchTypeList.put(bundle.getString("book_name"), SearchType.TITLE);
    }

    public Map<String, SearchType> getSearchTypeList() {
        return searchTypeList;
    }        
}
