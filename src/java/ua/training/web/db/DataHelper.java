/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.training.web.db;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import ua.training.web.entity.Author;
import ua.training.web.entity.Book;
import ua.training.web.entity.Genre;
import ua.training.web.entity.HibernateUtil;

/**
 *
 * @author Alexandr
 */

public class DataHelper {
    private SessionFactory sessionFactory;
    private static DataHelper dataHelper;
    
    private DataHelper() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }
    
    public static DataHelper getInstance() {
        return dataHelper == null ? new DataHelper() : dataHelper;
    }
    
    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }
    
    public List<Book> getAllBooks() {
        return getSession().createCriteria(Book.class).addOrder(Order.asc("name")).list();
    }
    
    public List<Genre> getAllGenres() {
        return getSession().createCriteria(Genre.class).addOrder(Order.asc("name")).list();
    }
    
    public List<Author> getAllAuthors() {
        return getSession().createCriteria(Author.class).list();
    }
    
    public List<Book> getBooksByGenre(long genreId) {
        return getSession().createCriteria(Book.class).add(Restrictions.eq("genre.id", genreId)).addOrder(Order.asc("name")).list();
    }
    
    public List<Book> getBooksByLetter(Character letter) {
        return getBooksList("name", letter.toString(), MatchMode.START);
    }
    
    public List<Book> getBooksByAuthor(String name) {
        return getSession().createCriteria(Book.class).addOrder(Order.asc("name")).createCriteria("author", "a").add(Restrictions.ilike("a.fio", name, MatchMode.ANYWHERE)).list();
    }
    
    public List<Book> getBooksByName(String bookName) {
        return getBooksList("name", bookName, MatchMode.ANYWHERE);
    }
    
    public byte[] getContent(Long id) {
        return (byte[])getFieldValue("content", id);
    }
    
    public byte[] getImage(Long id) {
        return (byte[])getFieldValue("image", id);
    }
    
    private List<Book> getBooksList(String propertyName, String value, MatchMode matchMode) {
        return getSession().createCriteria(Book.class).add(Restrictions.ilike(propertyName, value, matchMode)).addOrder(Order.asc("name")).list();
    }    
    
    private Object getFieldValue(String field, Long id) {        
        return getSession().createCriteria(Book.class).setProjection(Projections.property(field)).add(Restrictions.eq("id", id)).uniqueResult();
    }
}
