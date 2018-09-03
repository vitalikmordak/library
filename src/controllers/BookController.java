package controllers;

import beans.Paginator;
import db.Database;
import entities.Book;
import enums.SearchType;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Named
@SessionScoped
public class BookController implements Serializable {
    private List<Book> bookList;
    private String searchString;
    private long selectedGenreId; // selected genre
    private char selectedLetter; // selected letter
    private boolean selectedAllBooks = false; // Is "All books" selected?
    private boolean editMode; // to ON|OFF edit mode
    private Paginator<Book> paginator = new Paginator<>();
    private Database dbInstance = Database.getInstance();


    public BookController() {
        getAllBooks();
        bookList = paginator.getList();
    }

    public void getAllBooks() {
        setDefaultValues(0, ' ', true);
        dbInstance.getAllBooks(paginator);
    }

    // Create bookList by genre
    public void getBooksByGenre() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        setDefaultValues(Long.valueOf(params.get("genre_id")), ' ', false);
        dbInstance.getBooksByGenre(selectedGenreId, paginator);
    }

    public void getBooksByLetter() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        setDefaultValues(0, params.get("letter").charAt(0), false);
        dbInstance.getBooksByLetter(selectedLetter, paginator);
    }

    @Inject
    SearchController searchController;

    public void getBooksBySearch() {
        setDefaultValues(0, ' ', false);
        if (searchString.trim().length() == 0) {
            getAllBooks();
            return;
        }
        SearchType type = searchController.getSearchType();

        if (type == SearchType.AUTHOR) {
            dbInstance.getBooksByAuthor(searchString, paginator);
        } else if (type == SearchType.TITLE) {
            dbInstance.getBooksByName(searchString, paginator);
        }
    }

    public void selectPage() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        paginator.setSelectedPageNumber(Integer.valueOf(params.get("page_number")));
        dbInstance.runCriteria();
    }

    // Update book data
    public void updateBook() {
       /* PreparedStatement preparedStat = null;
        Connection conn = null;
        try {
            conn = Database.getConnection();
            preparedStat = conn.prepareStatement("update book set name=?, isbn=?, page_count=?, publish_year=? where id=?");

            for (Book book : bookList) {
                if (!book.isEdit()) continue; // if current book does not need editing, skip it
                preparedStat.setString(1, book.getName());
                preparedStat.setString(2, book.getIsbn());
//                preparedStat.setString(3,book.getAuthor());
                preparedStat.setInt(3, book.getPageCount());
                preparedStat.setInt(4, book.getPublishDate());
                preparedStat.setLong(5, book.getId());
                preparedStat.addBatch();
            }
            preparedStat.executeBatch();
        } catch (SQLException e) {
            Logger.getLogger(BookController.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            Database.closeConnections(conn, preparedStat, null);
        }*/
        cancel();
    }

    // Change editMode
    public void switchEditMode() {
        editMode = !editMode;
    }

    public void cancel() {
        switchEditMode();
        // clear checkboxes
        bookList.forEach(book -> book.setEdit(false));
    }

    public boolean getEditMode() {
        return editMode;
    }

    // Set default values
    private void setDefaultValues(long selectedGenreId, char selectedLetter, boolean selectedAllBooks) {
        paginator.setSelectedPageNumber(1);
        this.selectedGenreId = selectedGenreId;
        this.selectedLetter = selectedLetter;
        this.selectedAllBooks = selectedAllBooks;
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

    public long getSelectedGenreId() {
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

    public Paginator<Book> getPaginator() {
        return paginator;
    }

    public boolean isSelectedAllBooks() {
        return selectedAllBooks;
    }

    public void setSelectedAllBooks(boolean selectedAllBooks) {
        this.selectedAllBooks = selectedAllBooks;
    }
}
