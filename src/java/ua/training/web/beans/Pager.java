/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.training.web.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alexandr
 * @param <T>
 */
public class Pager<T> implements Serializable {
    
    private List<T> list;
    private int selectedPageNumber = 1;
    private int booksCountOnPage = 5;
    private long totalBooksCount;
    private final List<Integer> pageNumbers = new ArrayList<Integer>();
    
    public int getFrom() {
        return selectedPageNumber * booksCountOnPage - booksCountOnPage;
    }
    
    public int getTo() {
        return booksCountOnPage;
    }
    
    public List<T> getList() {
        return list;
    }        
    
    public void setList(List<T> list) {
        this.list = list;
    }
    
    public int getSelectedPageNumber() {
        return selectedPageNumber;
    }
    
    public void setSelectedPageNumber(int selectedPageNumber) {
        this.selectedPageNumber = selectedPageNumber;
    }
    
    public int getBooksCountOnPage() {
        return booksCountOnPage;
    }
    
    public void setBooksCountOnPage(int booksCountOnPage) {
        this.booksCountOnPage = booksCountOnPage;
    }
    
    public long getTotalBooksCount() {
        return totalBooksCount;
    }        
    
    public void setTotalBooksCount (long totalBooksCount) {
        this.totalBooksCount = totalBooksCount;
    }
    
    public List<Integer> getPageNumbers() {
        int pageCount = 0;
        
        if (totalBooksCount % booksCountOnPage == 0) {
            pageCount = booksCountOnPage > 0 ? (int) (totalBooksCount / booksCountOnPage) : 0;
        } else {
            pageCount = booksCountOnPage > 0 ? (int) (totalBooksCount / booksCountOnPage) + 1 : 0;
        }
        
        pageNumbers.clear();
        
        for (int i = 1; i <= pageCount; i++) {
            pageNumbers.add(i);
        }
        return pageNumbers;
    }
}
