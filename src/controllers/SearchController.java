package controllers;

import enums.SearchType;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

@Named
@ApplicationScoped
public class SearchController {
    private SearchType searchType;
    private static final Map<String, SearchType> searchList = new HashMap<>();

    public SearchController() {
        ResourceBundle bundle = ResourceBundle.getBundle("nls.messages", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        searchList.put(bundle.getString("author_enum"), SearchType.AUTHOR);
        searchList.put(bundle.getString("book_title_enum"), SearchType.TITLE);
    }

    public SearchType getSearchType() {
        return searchType;
    }

    public Map<String, SearchType> getSearchList() {
        return searchList;
    }

    // get letters array
    public Character[] getLetters() {
        return "abcdefghijklmnopqrstuvwxyz".toUpperCase().chars().mapToObj(c -> (char) c).toArray(Character[]::new);
    }

    public void setSearchType(SearchType searchType) {
        this.searchType = searchType;
    }
}
