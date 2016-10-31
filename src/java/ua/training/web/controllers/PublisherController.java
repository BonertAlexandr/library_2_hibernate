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
import ua.training.web.entity.Publisher;

/**
 *
 * @author Alexandr
 */

@ManagedBean(eager = false)
@ApplicationScoped
public class PublisherController implements Serializable, Converter{
    
    private List<SelectItem> selectItems = new ArrayList<SelectItem>();
    private List<Publisher> list;
    private Map<Long, Publisher> map;
    
    public PublisherController() {
        list =  DataHelper.getInstance().getAllPublishers();
        map = new HashMap<Long, Publisher>();
        Collections.sort(list, ListComporator.getInstance());

        for (Publisher p : list) {
            selectItems.add(new SelectItem(p, p.getName()));
            map.put(p.getId(), p);
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
        return ((Publisher)value).getId().toString();
    }
    
}
