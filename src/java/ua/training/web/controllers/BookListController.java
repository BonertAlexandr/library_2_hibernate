/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.training.web.controllers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import ua.training.web.db.DataHelper;
import ua.training.web.entity.Book;
import ua.training.web.enums.SearchType;

/**
 *
 * @author Alexandr
 */
@ManagedBean(eager = true)
@SessionScoped
public class BookListController implements Serializable {

    private boolean requestFromPage; //check from where request came
    private SearchType searchType; //stores the value of search type(search by author or by title of the book)
    private List<Book> currentBookList; //current book list to display
    private String searchString; //input value from search string
    private int totalBooksCount;
    private int booksOnPage = 5;//the default numbers of book to display
    private String currentSql; 
    private ArrayList<Integer> pageNumbers = new ArrayList<Integer>(); 
    private int selectedPageNumber = 1;
    private int selectedGenreId;
    private char selectedLetter;
    private boolean editMode;        
    
    public BookListController() {
        fillBooksAll();        
    }   
    
    public void showEdit() {
        editMode = true;
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
    
    public int getSelectedGenreId() {
        return selectedGenreId;
    }
    
    public void setBooksOnPage(int booksOnPage) {
        this.booksOnPage = booksOnPage;
    }
    
    public int getBooksOnPage() {
        return booksOnPage;
    }
    
    public void setPageNumbers(ArrayList<Integer> pageNumbers) {
        this.pageNumbers = pageNumbers;
    }
    
    public ArrayList<Integer> getPageNumbers() {
        return pageNumbers;
    }
    
    public void setSelectedPageNumber(int selectedPageNumber) {
        this.selectedPageNumber = selectedPageNumber;
    }
    
    public int getSelectedPageNumber() {
        return selectedPageNumber;
    }
    
    public void setTotalBooksCount(int totalBooksCount) {
        this.totalBooksCount = totalBooksCount;
    }
    
    public int getTotalBooksCount() {
        return totalBooksCount;
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

    public List<Book> getCurrentBookList() {
        return currentBookList;
    }
    
    public String getSearchString() {
        return searchString;
    }

    private void fillBooksAll() {
        currentBookList = DataHelper.getInstance().getAllBooks();
        fillPageNumbers(currentBookList.size(), booksOnPage);
    }   
    
    public void fillBooksByGenre() {
        cancelEdit();
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();        
        int genreId = Integer.parseInt(params.get("genre_id"));
        submitValue(genreId, ' ', 1, false);
        currentBookList = DataHelper.getInstance().getBooksByGenre(selectedGenreId);
    }    
    
    public void fillBooksByLetter() {
        cancelEdit();
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();        
        submitValue(-1, params.get("letter").charAt(0), 1, false);
        currentBookList = DataHelper.getInstance().getBooksByLetter(selectedLetter);
    }
    
    public void fillBooksBySearch() {  
        cancelEdit();        
        submitValue(-1, ' ', 1, false);
        
        if (searchString.trim().length() == 0) {
            currentBookList = DataHelper.getInstance().getAllBooks();
        } else {            
            if (searchType == SearchType.TITLE){
                currentBookList = DataHelper.getInstance().getBooksByName(searchString);
            } else {                
                currentBookList = DataHelper.getInstance().getBooksByAuthor(searchString);
            }            
        }
        System.out.println("fillBooksBySearch()");
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

    private void fillPageNumbers(int totalBooksCount, int booksOnPage) {                   
        int pageCount;
        if(totalBooksCount % booksOnPage == 0) {
            pageCount = totalBooksCount > 0 ? totalBooksCount / booksOnPage : 0;
        } else {
            pageCount = totalBooksCount > 0 ? totalBooksCount / booksOnPage + 1 : 0;
        }                
        pageNumbers.clear();
        
        for (int i = 1; i <= pageCount; i++) {
            pageNumbers.add(i);
        }
    }
    
    public String selectPage() {
        cancelEdit();
        requestFromPage = true;
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        selectedPageNumber = Integer.parseInt(params.get("page_number"));
//        fillBooksBySQL(currentSql);
        return "books";
    }
    
    private void submitValue(int selectedGenreId, char selectedLetter, int selectedPageNumber, boolean requestFromPage) {
        this.selectedGenreId = selectedGenreId;
        this.selectedLetter = selectedLetter;
        this.selectedPageNumber = selectedPageNumber;
        this.requestFromPage = requestFromPage;
    }
    
    public void updateBooks() {
        cancelEdit();
        
    }
    
    public void cancelEdit() {
        editMode = false;
//        for(Book b : currentBookList) {
//            b.setEdit(false);
//        }
    }
    
    public void booksOnPageChangedListener(ValueChangeEvent e) {        
        cancelEdit();
        requestFromPage = false;
        booksOnPage = Integer.parseInt(e.getNewValue().toString());
        selectedPageNumber = 1;
//        fillBooksBySQL(currentSql);
    }
}
