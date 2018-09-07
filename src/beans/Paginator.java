package beans;

import entities.Book;

import java.util.ArrayList;
import java.util.List;

/*
 * Class for pagination
 */
public class Paginator {
    private int selectedPageNumber = 1; // selected page number
    private int booksOnPage = 3; // number of books displayed on the page
    private int countAllBooks = 0; // number of all books
    private List<Integer> pageNumber = new ArrayList<>(); // number of pages for pagination
    private List<Book> list; //Book list
    private int firstResult;
    private static Paginator paginator;

    public static Paginator getInstance() {
        return paginator == null ? paginator = new Paginator() : paginator;
    }


    public List<Integer> getPageNumber() {
        int pageCount;
        if (countAllBooks % booksOnPage == 0)
            pageCount = booksOnPage > 0 ? (int) (countAllBooks / booksOnPage) : 0;
        else pageCount = booksOnPage > 0 ? (int) (countAllBooks / booksOnPage) + 1 : 0;

        pageNumber.clear();
        for (int i = 1; i <= pageCount; i++) {
            pageNumber.add(i);
        }
        return pageNumber;
    }

    public int getFirstResult() {
        return firstResult;
//        return selectedPageNumber * booksOnPage - booksOnPage;
    }

    public void setBooksOnPage(int booksOnPage) {
        this.booksOnPage = booksOnPage;
    }

    public void setFirstResult(int firstResult) {
        this.firstResult = firstResult;
    }

    public int getSelectedPageNumber() {
        return selectedPageNumber;
    }

    public void setSelectedPageNumber(int selectedPageNumber) {
        this.selectedPageNumber = selectedPageNumber;
    }

    public int getBooksOnPage() {
        return booksOnPage;
    }

    public int getCountAllBooks() {
        return countAllBooks;
    }

    public void setCountAllBooks(int countAllBooks) {
        this.countAllBooks = countAllBooks;
    }

    public List<Book> getList() {
        return list;
    }

    public void setList(List<Book> list) {
        this.list = list;
    }
}
