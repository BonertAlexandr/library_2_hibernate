/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.training.web.beans;

import java.util.List;
import ua.training.web.entity.Book;

/**
 *
 * @author Alexandr
 */
public class Pager {
        
    private static Pager pager;
    
    private int rowIndex;
    
    private Pager() {
        
    }
    
    public static Pager getInstance() {
        if(pager == null) {
            pager = new Pager();
        }
        return pager;
    }
    
    private List<Book> list;        
    private long totalBooksCount;
    private int from;
    private int to;
    private int selectedBook;
    
    public int getFrom() {
        return this.from;
    }
    
    public void setFrom(int from) {
        this.from = from;
    }
    
    public int getTo() {
        return this.to;
    }
    
    public void setTo(int to) {
        this.to = to;
    }
    
    public List getList() {        
        return list;
    }        
    
    public void setList(List list) {
        rowIndex = -1;
        this.list = list;
    }        
    
    public int getRowIndex() {
        rowIndex += 1;
        return rowIndex;
    }
    
    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }
    
    public long getTotalBooksCount() {
        return totalBooksCount;
    }        
    
    public void setTotalBooksCount (long totalBooksCount) {
        this.totalBooksCount = totalBooksCount;
    }
        
}
