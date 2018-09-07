package models;

import beans.Paginator;
import db.Database;
import entities.Book;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import java.util.List;
import java.util.Map;

public class BookListLazyModel extends LazyDataModel<Book> {
    private Paginator paginator = Paginator.getInstance();

    @Override
    public List<Book> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        paginator.setFirstResult(first);
        paginator.setBooksOnPage(pageSize);
        Database.getInstance().runCriteria();
        Database.getInstance().countBooks();
        this.setRowCount(paginator.getCountAllBooks());
        return paginator.getList();
    }

    @Override
    public Book getRowData(String rowKey) {
        for (Book book : paginator.getList()) {
            if (book.getId().intValue() == Long.valueOf(rowKey).intValue())
                return book;

        }
        return null;
    }

    @Override
    public Object getRowKey(Book book) {
        return book.getId();
    }
}
