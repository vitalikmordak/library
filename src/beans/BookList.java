package beans;

import db.Database;
import enums.SearchType;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BookList {
    private List<Book> bookList = new ArrayList<>();


    private ArrayList<Book> getBooks(String s) {
        Connection connection = null;
        Statement stat = null;
        ResultSet rs = null;
        try {
            connection = Database.getConnection();

            stat = connection.createStatement();
            rs = stat.executeQuery(s);
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
            Logger.getLogger(BookList.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (stat != null) stat.close();
                if (connection != null) connection.close();
                if (rs != null) rs.close();
            } catch (SQLException e) {
                Logger.getLogger(BookList.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        return (ArrayList<Book>) bookList;
    }

    public ArrayList<Book> getAllBooks() {
        if (!bookList.isEmpty()) {
            return (ArrayList<Book>) bookList;
        } else return getBooks("select b.id, b.name, b.isbn, b.page_count, b.publish_year, " +
                "p.name as publisher, a.fio as author, g.name as genre, b.image from book b " +
                "inner join author a on b.author_id=a.id " +
                "inner join genre g on b.genre_id=g.id " +
                "inner join publisher p on b.publisher_id=p.id " +
                "order by b.name limit 0,5");
    }

    // Create bookList by genre
    public ArrayList<Book> getBooksByGenre(long id) {
        if (id == 0) {
            return getAllBooks();
        } else {
            return getBooks("select b.id, b.name, b.isbn, b.page_count, b.publish_year, " +
                    "p.name as publisher, a.fio as author, g.name as genre, b.image from book b " +
                    "inner join author a on b.author_id=a.id " +
                    "inner join genre g on b.genre_id=g.id " +
                    "inner join publisher p on b.publisher_id=p.id " +
                    "where genre_id=" + id + " order by b.name " +
                    "limit 0,5");
        }
    }

    public ArrayList<Book> getBooksByLetter(String letter) {
        return getBooks("select b.id, b.name, b.isbn, b.page_count, b.publish_year, " +
                "p.name as publisher, a.fio as author, g.name as genre, b.image from book b " +
                "inner join author a on b.author_id=a.id " +
                "inner join genre g on b.genre_id=g.id " +
                "inner join publisher p on b.publisher_id=p.id " +
                "where substr(b.name, 1, 1)='" + letter + "' order by b.name " +
                "limit 0,5");
    }

    public ArrayList<Book> getBooksBySearch(String searchStr, SearchType type) {
        StringBuilder sqlRequest = new StringBuilder("select b.id, b.name, b.isbn, b.page_count, b.publish_year, " +
                "p.name as publisher, a.fio as author, g.name as genre, b.image from book b " +
                "inner join author a on b.author_id=a.id " +
                "inner join genre g on b.genre_id=g.id " +
                "inner join publisher p on b.publisher_id=p.id ");
        if (type == SearchType.AUTHOR) {
            sqlRequest.append("where lower(a.fio) like '%" + searchStr.toLowerCase() + "%' order by b.name ");
        } else if (type == SearchType.TITLE) {
            sqlRequest.append("where lower(b.name) like '%" + searchStr.toLowerCase() + "%' order by b.name ");
        }
        sqlRequest.append("limit 0,5");
        return getBooks(sqlRequest.toString());
    }
}
