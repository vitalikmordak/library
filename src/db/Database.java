package db;

import entities.Author;
import entities.Book;
import entities.Genre;
import entities.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class Database {
    private SessionFactory sessionFactory;
    private static Database database;

    private Database() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    public static Database getInstance() {
        return database == null ? new Database() : database;
    }

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    public List<Book> getAllBooks() {
        CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();
        CriteriaQuery<Book> criteriaQuery = criteriaBuilder.createQuery(Book.class);
        criteriaQuery.from(Book.class);
        return getSession().createQuery(criteriaQuery).getResultList();
    }

    public List<Author> getAllAuthors() {
        CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();
        CriteriaQuery<Author> criteriaQuery = criteriaBuilder.createQuery(Author.class);
        criteriaQuery.from(Author.class);
        return getSession().createQuery(criteriaQuery).getResultList();
    }

    public List<Genre> getAllGenres() {
        CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();
        CriteriaQuery<Genre> criteriaQuery = criteriaBuilder.createQuery(Genre.class);
        criteriaQuery.from(Genre.class);
        return getSession().createQuery(criteriaQuery).getResultList();
    }

    public List<Book> getBooksByGenre(Long genreId) {
        CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();
        CriteriaQuery<Book> criteriaQuery = criteriaBuilder.createQuery(Book.class);
        Root<Book> root = criteriaQuery.from(Book.class);
        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.equal(root.get("genreId"), genreId));
        return getSession().createQuery(criteriaQuery).getResultList();
    }

    public List<Book> getBooksByLetter(Character letter) {

        return getBookList("name", letter.toString(), "start");
    }

    public List<Book> getBooksByAuthor(String authorName) {

        return getBookList("author", authorName, "anywhere");
    }

    public List<Book> getBooksByName(String bookName) {

        return getBookList("name", bookName, "anywhere");
    }

    private List<Book> getBookList(String field, String value, String matchMode) {
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
        return getSession().createQuery(criteriaQuery).getResultList();
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
