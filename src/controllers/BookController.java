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
    private int booksOnPage = 6; // number of books displayed on the page
    private int selectedGenreId; // selected genre
    private char selectedLetter; // selected letter
    private long countAllBooks; // number of all books
    private int selectedPageNumber = 0; // selected page number
    private List<Integer> pageNumber = new ArrayList<>();
    private String currentSql; // the last sql executed without adding a limit
    private boolean isSelectedAllBooks = false;

    public BookController() {
        getAllBooks();
    }

    private void getBooks(String s) {
        StringBuilder sqlBuilder = new StringBuilder(s);
        currentSql = s;
        Connection connection = null;
        Statement stat = null;
        ResultSet rs = null;
        try {
            connection = Database.getConnection();
            stat = connection.createStatement();
            rs = stat.executeQuery(sqlBuilder.toString()); // Execute the request without limit
            rs.last(); // Moves the cursor to the end of the request
            countAllBooks = rs.getRow(); // get number of all books
            fillPageNumbers(countAllBooks, booksOnPage);
            // add limit if need
            if (countAllBooks > booksOnPage) {
                sqlBuilder.append(" limit ").append(selectedPageNumber * booksOnPage).append(",").append(booksOnPage);
            }
            rs = stat.executeQuery(sqlBuilder.toString());

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
            closeConnections(connection, stat, rs);
        }
    }

    private void fillPageNumbers(long countAllBooks, int countBooksOnPage) {
        int pageCount = countAllBooks > 0 ? (int) (countAllBooks / countBooksOnPage) : 0;

        pageNumber.clear(); // clear before each request
        for (int i = 0; i <= pageCount; i++) {
            pageNumber.add(i);
        }
    }


    public void getAllBooks() {
        getBooks("select b.id, b.name, b.isbn, b.page_count, b.publish_year, " +
                "p.name as publisher, a.fio as author, g.name as genre, b.image from book b " +
                "inner join author a on b.author_id=a.id " +
                "inner join genre g on b.genre_id=g.id " +
                "inner join publisher p on b.publisher_id=p.id " +
                "order by b.name");
        isSelectedAllBooks = true;
        selectedLetter = ' ';
        selectedGenreId = 0;
        selectedPageNumber = 0;
    }

    // Create bookList by genre
    public void getBooksByGenre() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        selectedGenreId = Integer.valueOf(params.get("genre_id"));
        getBooks("select b.id, b.name, b.isbn, b.page_count, b.publish_year, " +
                "p.name as publisher, a.fio as author, g.name as genre, b.image from book b " +
                "inner join author a on b.author_id=a.id " +
                "inner join genre g on b.genre_id=g.id " +
                "inner join publisher p on b.publisher_id=p.id " +
                "where genre_id=" + selectedGenreId + " order by b.name ");
        selectedPageNumber = 0;
        selectedLetter = ' ';
        isSelectedAllBooks = false;
    }

    public void getBooksByLetter() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        selectedLetter = params.get("letter").charAt(0);
        getBooks("select b.id, b.name, b.isbn, b.page_count, b.publish_year, " +
                "p.name as publisher, a.fio as author, g.name as genre, b.image from book b " +
                "inner join author a on b.author_id=a.id " +
                "inner join genre g on b.genre_id=g.id " +
                "inner join publisher p on b.publisher_id=p.id " +
                "where substr(b.name, 1, 1)='" + selectedLetter + "' order by b.name ");
        selectedGenreId = 0;
        selectedPageNumber = 0;
        isSelectedAllBooks = false;
    }

    @Inject
    SearchController searchController;

    public void getBooksBySearch() {
        if (searchString.trim().length() == 0) {
            getAllBooks();
            return;
        }
        SearchType type = searchController.getSearchType();
        StringBuilder sqlRequest = new StringBuilder("select b.id, b.name, b.isbn, b.page_count, b.publish_year, " +
                "p.name as publisher, a.fio as author, g.name as genre, b.image from book b " +
                "inner join author a on b.author_id=a.id " +
                "inner join genre g on b.genre_id=g.id " +
                "inner join publisher p on b.publisher_id=p.id ");
        if (type == SearchType.AUTHOR) {
            sqlRequest.append("where lower(a.fio) like '%").append(searchString.toLowerCase()).append("%' order by b.name ");
        } else if (type == SearchType.TITLE) {
            sqlRequest.append("where lower(b.name) like '%").append(searchString.toLowerCase()).append("%' order by b.name ");
        }

        getBooks(sqlRequest.toString());
        selectedGenreId = 0;
        selectedPageNumber = 0;
        selectedLetter = ' ';
        isSelectedAllBooks = false;
    }

    public void selectPage() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        selectedPageNumber = Integer.valueOf(params.get("page_number"));
        getBooks(currentSql);
    }

    // Close connection to DB
    public static void closeConnections(Connection connection, Statement stat, ResultSet rs) {
        try {
            if (stat != null) stat.close();
            if (connection != null) connection.close();
            if (rs != null) rs.close();
        } catch (SQLException e) {
            Logger.getLogger(BookController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /*
     *   Getters and Setters
     */
    public ArrayList<Book> getBookList() {
        return (ArrayList<Book>) bookList;
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    public int getBooksOnPage() {
        return booksOnPage;
    }

    public void setBooksOnPage(int booksOnPage) {
        this.booksOnPage = booksOnPage;
    }

    public int getSelectedGenreId() {
        return selectedGenreId;
    }

    public void setSelectedGenreId(int selectedGenreId) {
        this.selectedGenreId = selectedGenreId;
    }

    public char getSelectedLetter() {
        return selectedLetter;
    }

    public void setSelectedLetter(char selectedLetter) {
        this.selectedLetter = selectedLetter;
    }

    public long getCountAllBooks() {
        return countAllBooks;
    }

    public void setCountAllBooks(long countAllBooks) {
        this.countAllBooks = countAllBooks;
    }

    public long getSelectedPageNumber() {
        return selectedPageNumber;
    }

    public void setSelectedPageNumber(int selectedPageNumber) {
        this.selectedPageNumber = selectedPageNumber;
    }

    public List<Integer> getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(List<Integer> pageNumber) {
        this.pageNumber = pageNumber;
    }

    public boolean isSelectedAllBooks() {
        return isSelectedAllBooks;
    }

    public void setSelectedAllBooks(boolean selectedAllBooks) {
        isSelectedAllBooks = selectedAllBooks;
    }
}
