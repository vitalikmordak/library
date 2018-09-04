package beans;

import java.util.ArrayList;
import java.util.List;

/*
 * Class for pagination
 */
public class Paginator<T> {
    private int selectedPageNumber = 1; // selected page number
    private int booksOnPage = 3; // number of books displayed on the page
    private long countAllBooks = 0; // number of all books
    private List<Integer> pageNumber = new ArrayList<>(); // number of pages for pagination
    private List<T> list; //Book list

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
        return selectedPageNumber * booksOnPage - booksOnPage;
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

    public long getCountAllBooks() {
        return countAllBooks;
    }

    public void setCountAllBooks(long countAllBooks) {
        this.countAllBooks = countAllBooks;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}