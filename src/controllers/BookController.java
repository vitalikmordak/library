package controllers;

import beans.Paginator;
import db.Database;
import entities.Book;
import entities.HibernateUtil;
import enums.SearchType;
import models.BookListLazyModel;
import org.hibernate.Session;
import org.primefaces.PrimeFaces;
import org.primefaces.model.LazyDataModel;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Map;
import java.util.ResourceBundle;

@Named
@SessionScoped
public class BookController implements Serializable {
    private String searchString;
    private long selectedGenreId; // selected genre
    private char selectedLetter; // selected letter
    private boolean selectedAllBooks = false; // Is "All books" selected?
    private Paginator paginator = Paginator.getInstance();
    private Book selectedBook; // selected book in edit mode
    private boolean addMode;
    private ResourceBundle bundle = ResourceBundle.getBundle("nls.messages", FacesContext.getCurrentInstance().getViewRoot().getLocale());


    private LazyDataModel model;

    public BookController() {
        getAllBooks();
        model = new BookListLazyModel();
        selectedBook = new Book();
    }

    public LazyDataModel getModel() {
        return model;
    }

    public void getAllBooks() {
        setDefaultValues(0, ' ', true);
        Database.getInstance().getAllBooks(paginator);
    }

    // Create bookList by genre
    public void getBooksByGenre() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        setDefaultValues(Long.valueOf(params.get("genre_id")), ' ', false);
        Database.getInstance().getBooksByGenre(selectedGenreId, paginator);
    }

    public void getBooksByLetter() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        setDefaultValues(0, params.get("letter").charAt(0), false);
        Database.getInstance().getBooksByLetter(selectedLetter, paginator);
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
            Database.getInstance().getBooksByAuthor(searchString, paginator);
        } else if (type == SearchType.TITLE) {
            Database.getInstance().getBooksByName(searchString, paginator);
        }
    }

    // Update book data
    public void updateBook() {
        if (!validateFields()) return;

        try {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            if (!addMode)
                session.update(selectedBook);
            else session.save(selectedBook);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Database.getInstance().runCriteria(); // update book list after change, e.g. genre
        Database.getInstance().countBooks(); // update countAllBooks after change
        cancel();
    }

    public void cancel() {
        PrimeFaces.current().dialog().closeDynamic(selectedBook); // Close dialog
    }

    // Set default values

    private void setDefaultValues(long selectedGenreId, char selectedLetter, boolean selectedAllBooks) {
        paginator.setSelectedPageNumber(1);
        this.selectedGenreId = selectedGenreId;
        this.selectedLetter = selectedLetter;
        this.selectedAllBooks = selectedAllBooks;
    }

    public void switchAddMode() {
        addMode = !addMode;
    }

    // Validate fields while edit or add book
    private boolean validateFields() {
        if (checkField(selectedBook.getName()) ||
                checkField(selectedBook.getIsbn()) ||
                checkField(selectedBook.getPageCount()) ||
                checkField(selectedBook.getPublishDate())) {
            printFailMsg(bundle.getString("empty_fields"));
            return false;
        }
        if (addMode) {
            if (checkField(selectedBook.getImage())) {
                printFailMsg(bundle.getString("image_not_uploaded"));
                return false;
            }
            if (checkField(selectedBook.getContent())) {
                printFailMsg(bundle.getString("content_not_uploaded"));
                return false;
            }
        }
        return true;
    }

    private boolean checkField(Object o) {
        return o == null || o.toString().equals("");
    }

    private void printFailMsg(String msg) {
        FacesContext.getCurrentInstance().validationFailed();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, "error"));
    }

    /*
     *   Getters and Setters
     */
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

    public Paginator getPaginator() {
        return paginator;
    }

    public boolean isSelectedAllBooks() {
        return selectedAllBooks;
    }

    public void setSelectedAllBooks(boolean selectedAllBooks) {
        this.selectedAllBooks = selectedAllBooks;
    }

    public Book getSelectedBook() {
        return selectedBook;
    }

    public void setSelectedBook(Book selectedBook) {
        this.selectedBook = selectedBook;
    }

    public boolean isAddMode() {
        return addMode;
    }

    public void setAddMode(boolean addMode) {
        this.addMode = addMode;
    }
}
