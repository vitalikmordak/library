package controllers;

import beans.Book;
import db.Database;
import enums.SearchType;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@SessionScoped
public class BookController implements Serializable {
    private List<Book> bookList;
    private String searchString;

    private void getBooks(String s) {
        Connection connection = null;
        Statement stat = null;
        ResultSet rs = null;
        try {
            connection = Database.getConnection();

            stat = connection.createStatement();
            rs = stat.executeQuery(s);
            bookList = new ArrayList<>();
            while (rs.next()) {
                // Create instance of Вook and add it in List
                Book book = new Book();
                book.setId(rs.getLong("id"));
                book.setName(rs.getString("name"));
                book.setGenre(rs.getString("genre"));
                book.setIsbn(rs.getString("isbn"));
                book.setAuthor(rs.getString("author"));
                book.setPageCount(rs.getInt("page_count"));
                book.setPublishDate(rs.getInt("publish_year"));
                book.setPublisher(rs.getString("publisher"));
                book.setImage(rs.getBytes("image"));
                bookList.add(book);
            }
        } catch (SQLException e) {
            Logger.getLogger(BookController.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (stat != null) stat.close();
                if (connection != null) connection.close();
                if (rs != null) rs.close();
            } catch (SQLException e) {
                Logger.getLogger(BookController.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    public ArrayList<Book> getBookList() {
        return (ArrayList<Book>) bookList;
    }

    public void getAllBooks() {
//        if (!bookList.isEmpty()) {
//            return (ArrayList<Book>) bookList;
//        } else return
        getBooks("select b.id, b.name, b.isbn, b.page_count, b.publish_year, " +
                "p.name as publisher, a.fio as author, g.name as genre, b.image from book b " +
                "inner join author a on b.author_id=a.id " +
                "inner join genre g on b.genre_id=g.id " +
                "inner join publisher p on b.publisher_id=p.id " +
                "order by b.name limit 0,5");
    }

    // Create bookList by genre
    public void getBooksByGenre() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        Integer id = Integer.valueOf(params.get("genre_id"));
//        if (id == 0) {
//            return getAllBooks();
//        } else {return
        getBooks("select b.id, b.name, b.isbn, b.page_count, b.publish_year, " +
                "p.name as publisher, a.fio as author, g.name as genre, b.image from book b " +
                "inner join author a on b.author_id=a.id " +
                "inner join genre g on b.genre_id=g.id " +
                "inner join publisher p on b.publisher_id=p.id " +
                "where genre_id=" + id + " order by b.name " +
                "limit 0,5");
//        }
    }

    public void getBooksByLetter() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        char letter = params.get("letter").charAt(0);
        getBooks("select b.id, b.name, b.isbn, b.page_count, b.publish_year, " +
                "p.name as publisher, a.fio as author, g.name as genre, b.image from book b " +
                "inner join author a on b.author_id=a.id " +
                "inner join genre g on b.genre_id=g.id " +
                "inner join publisher p on b.publisher_id=p.id " +
                "where substr(b.name, 1, 1)='" + letter + "' order by b.name " +
                "limit 0,5");
    }

    @Inject
    SearchController searchController;

    public void getBooksBySearch() {
        SearchType type = searchController.getSearchType();
        StringBuilder sqlRequest = new StringBuilder("select b.id, b.name, b.isbn, b.page_count, b.publish_year, " +
                "p.name as publisher, a.fio as author, g.name as genre, b.image from book b " +
                "inner join author a on b.author_id=a.id " +
                "inner join genre g on b.genre_id=g.id " +
                "inner join publisher p on b.publisher_id=p.id ");
        try {
            if (type == SearchType.AUTHOR) {
                sqlRequest.append("where lower(a.fio) like '%" + searchString.toLowerCase() + "%' order by b.name ");
            } else if (type == SearchType.TITLE) {
                sqlRequest.append("where lower(b.name) like '%" + searchString.toLowerCase() + "%' order by b.name ");
            }
            sqlRequest.append("limit 0,5");
        } catch (NullPointerException e) {
            Logger.getLogger(GenreController.class.getName()).log(Level.SEVERE, "Search string is empty", e);
        }
        getBooks(sqlRequest.toString());
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }
}
