package db;

import beans.Paginator;
import entities.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class Database {
    private SessionFactory sessionFactory;
    private static Database database;
    private Paginator currentPaginator = new Paginator();
    private CriteriaQuery criteria;

    private Database() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    public static Database getInstance() {
        return database == null ? database = new Database() : database;
    }

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    public void getAllBooks(Paginator paginator) {
        currentPaginator = paginator;
        CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();
        CriteriaQuery<Book> criteriaQuery = criteriaBuilder.createQuery(Book.class);
        Root<Book> book = criteriaQuery.from(Book.class);
        criteriaQuery.select(book).orderBy(criteriaBuilder.asc(book.get("name")));
        currentPaginator.setCountAllBooks(getSession().createQuery(criteriaQuery).stream().count());
        criteria = criteriaQuery;
        runCriteria();
    }

    public List<Author> getAllAuthors() {
        CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();
        CriteriaQuery<Author> criteriaQuery = criteriaBuilder.createQuery(Author.class);
        criteriaQuery.from(Author.class);
        return getSession().createQuery(criteriaQuery).getResultList();
    }

    public List<Publisher> getAllPublishers() {
        CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();
        CriteriaQuery<Publisher> criteriaQuery = criteriaBuilder.createQuery(Publisher.class);
        criteriaQuery.from(Publisher.class);
        return getSession().createQuery(criteriaQuery).getResultList();
    }

    public List<Genre> getAllGenres() {
        CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();
        CriteriaQuery<Genre> criteriaQuery = criteriaBuilder.createQuery(Genre.class);
        Root<Genre> root = criteriaQuery.from(Genre.class);
        criteriaQuery.orderBy(criteriaBuilder.asc(root.get("name")));
        return getSession().createQuery(criteriaQuery).getResultList();
    }

    public void getBooksByGenre(Long genreId, Paginator paginator) {
        currentPaginator = paginator;
        CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();
        CriteriaQuery<Book> criteriaQuery = criteriaBuilder.createQuery(Book.class);
        Root<Book> book = criteriaQuery.from(Book.class);
        criteriaQuery.select(book).where(criteriaBuilder.equal(book.get("genre").get("id"), genreId));
        criteriaQuery.orderBy(criteriaBuilder.asc(book.get("name")));
        currentPaginator.setCountAllBooks(getSession().createQuery(criteriaQuery).stream().count());
        criteria = criteriaQuery;
        runCriteria();
    }

    public void getBooksByLetter(Character letter, Paginator paginator) {
        getBookList("name", letter.toString(), "start", paginator);
    }

    public void getBooksByAuthor(String authorName, Paginator paginator) {
        getBookList("author", authorName, "anywhere", paginator);
    }

    public void getBooksByName(String bookName, Paginator paginator) {
        getBookList("name", bookName, "anywhere", paginator);
    }

    private void getBookList(String field, String value, String matchMode, Paginator paginator) {
        currentPaginator = paginator;
        CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();
        CriteriaQuery<Book> criteriaQuery = criteriaBuilder.createQuery(Book.class);
        Root<Book> root = criteriaQuery.from(Book.class);
        String condition = "";
        switch (matchMode) {
            case "start":
                condition = value + "%";
                break;
            case "anywhere":
                condition = "%" + value + "%";
                break;
        }
        if (field.equals("author")) {
            criteriaQuery.where(criteriaBuilder.like(root.get(field).get("fio"), condition));
        } else {
            criteriaQuery.where(criteriaBuilder.like(root.get(field), condition));
        }
        criteriaQuery.orderBy(criteriaBuilder.asc(root.get("name")));
        currentPaginator.setCountAllBooks(getSession().createQuery(criteriaQuery).stream().count());
        criteria = criteriaQuery;
        runCriteria();
    }

    // method that handles criteria queries per page
    @SuppressWarnings("unchecked")
    public void runCriteria() {
        Query<Book> query1 = getSession().createQuery(criteria);

        List<Book> list = query1.setFirstResult(currentPaginator.getFirstResult())
                .setMaxResults(currentPaginator.getBooksOnPage()).getResultList();

        currentPaginator.setList(list);
    }

    public byte[] getContent(Long id) {
        return (byte[]) getFieldValue("content", id);
    }

    public byte[] getImage(Long id) {
        return (byte[]) getFieldValue("image", id);
    }

    private Object getFieldValue(String field, Long id) {
        CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();
        CriteriaQuery<Book> criteriaQuery = criteriaBuilder.createQuery(Book.class);
        Root<Book> root = criteriaQuery.from(Book.class);
        criteriaQuery.select(root.get(field));

        criteriaQuery.where(criteriaBuilder.equal(root.get("id"), id));
        return getSession().createQuery(criteriaQuery).getSingleResult();
    }

    public Author getAuthor(long id) {
        return (Author) getSession().get(Author.class, id);
    }

}
