package controllers;

import db.Database;
import entities.Genre;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Named
@ApplicationScoped
public class GenreController implements Serializable, Converter {
    private List<Genre> genreList;
    private List<SelectItem> selectItems = new ArrayList<>();
    private Map<Long, Genre> genreMap;

    public GenreController() {
        genreMap = new HashMap<>();
        genreList = Database.getInstance().getAllGenres();
        genreList.forEach(genre -> {
            genreMap.put(genre.getId(), genre);
            selectItems.add(new SelectItem(genre, genre.getName()));
        });
    }

    private ArrayList<Genre> getGenres() {
//        genreList = Database.getInstance().getAllGenres();
        return (ArrayList<Genre>) genreList;
    }

    public ArrayList<Genre> getGenreList() {
        if (!genreList.isEmpty()) {
            return (ArrayList<Genre>) genreList;
        } else return getGenres();
    }

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        return genreMap.get(Long.valueOf(s));
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        return ((Genre)o).getId().toString();
    }

    public List<SelectItem> getSelectItems() {
        return selectItems;
    }
}
