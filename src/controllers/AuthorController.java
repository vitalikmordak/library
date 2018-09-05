package controllers;

import db.Database;
import entities.Author;

import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

@Named
@SessionScoped
public class AuthorController implements Serializable, Converter {
    private List<SelectItem> selectItems = new ArrayList<>();
    private Map<Long, Author> authorMap;
    private List<Author> authors;

    public AuthorController() {
        authorMap = new HashMap<>();
        //sort list
        authors = Database.getInstance().getAllAuthors().stream().sorted(Comparator.comparing(Author::getFio)).collect(Collectors.toList());
        authors.forEach(author -> {
            authorMap.put(author.getId(), author);
            selectItems.add(new SelectItem(author, author.getFio()));
        });

    }

    public List<SelectItem> getSelectItems() {
        return selectItems;
    }

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        return authorMap.get(Long.valueOf(s));
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        return ((Author) o).getId().toString();
    }

    public List<Author> getAuthors() {
        return authors;
    }
}
