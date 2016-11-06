/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.training.web.controllers;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.model.LazyDataModel;
import ua.training.web.beans.Pager;
import ua.training.web.db.DataHelper;
import ua.training.web.entity.Book;
import ua.training.web.enums.SearchType;
import ua.training.web.models.BookListDataModel;

/**
 *
 * @author Alexandr
 */
@ManagedBean(eager = true)
@SessionScoped
public class BookListController implements Serializable {
    
    private SearchType searchType; //stores the value of search type(search by author or by title of the book)
    private String searchString; //input value from search string                
    private long selectedGenreId;
    private char selectedLetter;
    private boolean editMode;        
    private Pager pager = Pager.getInstance();
    private LazyDataModel<Book> bookListModel;
    
    public BookListController() {        
        bookListModel = new BookListDataModel();
    }                       
    
    public void showEdit() {        
        editMode = true;
    }    
    
    public LazyDataModel<Book> getBookListModel() {
        return bookListModel;
    }        
    
    public Pager getPager() {
        return pager;
    }    
    
    public boolean getEditMode() {
        return editMode;
    }
    
    public char getSelectedLetter() {
        return selectedLetter;
    }
    
    public void setSelectedLetter(char selectedLetter) {
        this.selectedLetter = selectedLetter;
    }
    
    public void setSelectedGenreId(int selectedGenreId) {
        this.selectedGenreId = selectedGenreId;        
    }
    
    public long getSelectedGenreId() {
        return selectedGenreId;
    }
    
    public void setSearchType(SearchType searchType) {
        this.searchType = searchType;
    }
    
    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }
    
    public SearchType getSearchType() {
        return searchType;
    }
    
    public String getSearchString() {
        return searchString;
    }   
    
    public void fillBooksByGenre() {
        cancelEdit();        
        
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();        
        long genreId = Long.parseLong(params.get("genre_id"));        
        submitValue(genreId, ' ');                
        
        DataHelper.getInstance().getBooksByGenre(genreId);
    }    
    
    public void fillBooksByLetter() {
        cancelEdit();        
        
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();        
        submitValue(-1, params.get("letter").charAt(0));
        
        DataHelper.getInstance().getBooksByLetter(selectedLetter);
//        currentBookList = DataHelper.getInstance().getBooksByLetter(selectedLetter);
    }
    
    public void fillBooksBySearch() {  
        cancelEdit();        
        submitValue(-1, ' ');        
        
        if (searchString.trim().length() == 0) {
            DataHelper.getInstance().getAllBooks();
        } else {            
            if (searchType == SearchType.TITLE){
                DataHelper.getInstance().getBooksByName(searchString);
            } else {                
                DataHelper.getInstance().getBooksByAuthor(searchString);
            }            
        }
    }
    
    public Character[] getRussianLetters() {
        Character[] letters = new Character[33];
        letters[0] = 'А';
        letters[1] = 'Б';
        letters[2] = 'В';
        letters[3] = 'Г';
        letters[4] = 'Д';
        letters[5] = 'Е';
        letters[6] = 'Ё';
        letters[7] = 'Ж';
        letters[8] = 'З';
        letters[9] = 'И';
        letters[10] = 'Й';
        letters[11] = 'К';
        letters[12] = 'Л';
        letters[13] = 'М';
        letters[14] = 'Н';
        letters[15] = 'О';
        letters[16] = 'П';
        letters[17] = 'Р';
        letters[18] = 'С';
        letters[19] = 'Т';
        letters[20] = 'У';
        letters[21] = 'Ф';
        letters[22] = 'Х';
        letters[23] = 'Ц';
        letters[24] = 'Ч';
        letters[25] = 'Ш';
        letters[26] = 'Щ';
        letters[27] = 'Ъ';
        letters[28] = 'Ы';
        letters[29] = 'Ь';
        letters[30] = 'Э';
        letters[31] = 'Ю';
        letters[32] = 'Я';

        return letters;
    }        
    
    private void submitValue(long selectedGenreId, char selectedLetter) {
        this.selectedGenreId = selectedGenreId;
        this.selectedLetter = selectedLetter;        
    }
    
    public void updateBooks() {                   
        DataHelper.getInstance().update();        
        cancelEdit();   
        DataHelper.getInstance().populateList();
    }
    
    public void cancelEdit() {        
        editMode = false;

        List<Book> list = pager.getList();
        for (Book b : list) {
            b.setEdit(false);
        }    
    }            
}
