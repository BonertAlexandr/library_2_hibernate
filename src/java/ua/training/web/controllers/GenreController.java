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
import ua.training.web.entity.Genre;

/**
 *
 * @author Alexandr
 */

@ManagedBean(eager = false)
@ApplicationScoped
public class GenreController implements Serializable, Converter{
    private List<Genre> genreList;
    private List<SelectItem> selectItems = new ArrayList<SelectItem>();
    private Map<Long, Genre> map;
            
    public GenreController() {        
        fillGenresAll();        
        map = new HashMap<Long, Genre>();
        Collections.sort(genreList, ListComporator.getInstance());

        for (Genre g : genreList) {
            selectItems.add(new SelectItem(g, g.getName()));
            map.put(g.getId(), g);
        }
    }
    
    public List<Genre> getGenreList() {
        return genreList;
    }
    
    private void fillGenresAll() {
        genreList = DataHelper.getInstance().getAllGenres();
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
        return ((Genre)value).getId().toString();
    }
    
    
}
