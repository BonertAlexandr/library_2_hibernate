/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.training.web.controllers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;
import ua.training.web.comporators.ListComporator;
import ua.training.web.db.DataHelper;
import ua.training.web.entity.Author;

/**
 *
 * @author Alexandr
 */
@ManagedBean(eager = false)
@ApplicationScoped
public class AuthorController implements Serializable, Converter{

    private List<SelectItem> selectItems = new ArrayList<SelectItem>();
    private List<Author> list;
    private Map<Long, Author> map;
    
    public AuthorController() {        
        list = DataHelper.getInstance().getAllAuthors();
        map = new HashMap<Long, Author>();
        
        Collections.sort(list, ListComporator.getInstance());
        
        for (Author a : list) {
            selectItems.add(new SelectItem(a, a.getFio()));
            map.put(a.getId(), a);
        }
        
    }

    public List<SelectItem> getSelectItems() {
        return selectItems;
    }
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return map.get(Long.parseLong(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return ((Author) value).getId().toString();
    }
    
}
