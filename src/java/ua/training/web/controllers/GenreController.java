/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.training.web.controllers;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import ua.training.web.db.DataHelper;
import ua.training.web.entity.Genre;

/**
 *
 * @author Alexandr
 */

@ManagedBean(eager = false)
@ApplicationScoped
public class GenreController implements Serializable{
    private List<Genre> genreList;
    
    public GenreController() {        
        fillGenresAll();
    }
    
    public List<Genre> getGenreList() {
        return genreList;
    }
    
    private void fillGenresAll() {
        genreList = DataHelper.getInstance().getAllGenres();
    }
}
