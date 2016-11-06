/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.training.web.db;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import ua.training.web.beans.Pager;
import ua.training.web.entity.Author;
import ua.training.web.entity.Book;
import ua.training.web.entity.Genre;
import ua.training.web.entity.HibernateUtil;
import ua.training.web.entity.Publisher;

/**
 *
 * @author Alexandr
 */

public class DataHelper {
    private SessionFactory sessionFactory;
    private static DataHelper dataHelper;
    private DetachedCriteria bookListCriteria;
    private DetachedCriteria booksCountCriteria;
    private Pager pager = Pager.getInstance();
    private ProjectionList bookProjectionList;
    
    private DataHelper() {
        prepareCreterias();
        
        sessionFactory = HibernateUtil.getSessionFactory();
        
        bookProjectionList = Projections.projectionList();        
        bookProjectionList.add(Projections.property("id"), "id");
        bookProjectionList.add(Projections.property("name"), "name");
        bookProjectionList.add(Projections.property("image"), "image");
        bookProjectionList.add(Projections.property("genre"), "genre");
        bookProjectionList.add(Projections.property("pageCount"), "pageCount");
        bookProjectionList.add(Projections.property("isbn"), "isbn");
        bookProjectionList.add(Projections.property("publisher"), "publisher");
        bookProjectionList.add(Projections.property("author"), "author");
        bookProjectionList.add(Projections.property("publishYear"), "publishYear");
        bookProjectionList.add(Projections.property("descr"), "descr");
    }
    
    public static DataHelper getInstance() {
        if (dataHelper == null) {
            dataHelper = new DataHelper();
        }        
        System.out.println("RETURN DATAHELPER");
        return dataHelper;
    }
    
    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }
    
    public void getAllBooks() {                
        prepareCreterias();
        populateList();        
    }
    
    public List<Genre> getAllGenres() {
        return getSession().createCriteria(Genre.class).list();
    }
    
    public List<Author> getAllAuthors() {
        return getSession().createCriteria(Author.class).list();
    }
    
    public List<Publisher> getAllPublishers() {
        return getSession().createCriteria(Publisher.class).list();
    }
    
    public void getBooksByGenre(long genreId) {
        Criterion criterion = Restrictions.eq("genre.id", genreId);   
        prepareCreterias(criterion);                        
        populateList();
    }
    
    public void getBooksByLetter(Character letter) {                        
        Criterion criterion = Restrictions.ilike("name", letter.toString(), MatchMode.START);
        prepareCreterias(criterion);
        populateList();
    }
    
    public void getBooksByAuthor(String name) {        
        Criterion criterion = Restrictions.ilike("author.fio", name, MatchMode.ANYWHERE);
        prepareCreterias(criterion);
        populateList();
    }
    
    public void getBooksByName(String bookName) {
        Criterion criterion = Restrictions.ilike("name", bookName, MatchMode.ANYWHERE);
        prepareCreterias(criterion);
        populateList();
    }
    
    public byte[] getContent(Long id) {
        Criteria criteria = getSession().createCriteria(Book.class);        
        criteria.setProjection(Property.forName("content"));        
        criteria.add(Restrictions.eq("id", id));        
        return (byte[]) criteria.uniqueResult();
    }                    
    
    public void runBookListCriteria() {
        Criteria criteria = bookListCriteria.addOrder(Order.asc("name")).getExecutableCriteria(getSession());
        criteria.setProjection(bookProjectionList).setResultTransformer(Transformers.aliasToBean(Book.class));
        List<Book> list = criteria.setFirstResult(pager.getFrom()).setMaxResults(pager.getTo()).list();
        pager.setList(list);
    }

    public void update() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        
        for (Object o : pager.getList()) {
            Book book = (Book)o;
            if (book.isEdit()) {
                session.update(book);                
            }
        }
        
        transaction.commit();
        session.flush();
        session.close();
    }       

    private void runCountCriteria() {
        Criteria criteria = booksCountCriteria.getExecutableCriteria(getSession());
        Long total = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
        pager.setTotalBooksCount(total);
    }
    
    private void createAliases(DetachedCriteria criteria) {
        criteria.createAlias("b.author", "author");
        criteria.createAlias("b.genre", "genre");
        criteria.createAlias("b.publisher", "publisher");
    }  
    
    public void populateList() {
        runCountCriteria();
        runBookListCriteria();
    }

    private void prepareCreterias() {
        bookListCriteria = DetachedCriteria.forClass(Book.class, "b");
        createAliases(bookListCriteria);  
        
        booksCountCriteria = DetachedCriteria.forClass(Book.class, "b");
        createAliases(booksCountCriteria);
    }
    
    private void prepareCreterias(Criterion criterion) {
        bookListCriteria = DetachedCriteria.forClass(Book.class, "b");
        createAliases(bookListCriteria); 
        bookListCriteria.add(criterion);
        
        booksCountCriteria = DetachedCriteria.forClass(Book.class, "b");
        createAliases(booksCountCriteria);
        booksCountCriteria.add(criterion);
    }
}   