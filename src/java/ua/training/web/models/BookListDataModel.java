/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.training.web.models;

import java.util.List;
import java.util.Map;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import ua.training.web.beans.Pager;
import ua.training.web.db.DataHelper;
import ua.training.web.entity.Book;

/**
 *
 * @author Alexandr
 */
public class BookListDataModel extends LazyDataModel<Book> {
    private Pager pager = Pager.getInstance();
    private List<Book> bookList;
    private DataHelper dataHelper = DataHelper.getInstance();  

    public BookListDataModel() {
    }    
    
    @Override
    public Book getRowData(String rowKey) {
        for (Book b : bookList) {
            if(b.getId().intValue() == Long.valueOf(rowKey).intValue()) {
                return b;
            }
        }
        return null;
    }
    
    @Override
    public Object getRowKey(Book book) {
        return book.getId();
    }    

    @Override
    public List<Book> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        pager.setFrom(first);
        pager.setTo(pageSize);
        
        dataHelper.populateList();
        this.setRowCount((int)pager.getTotalBooksCount());
        
        return pager.getList();
    }
    
    
    
}
