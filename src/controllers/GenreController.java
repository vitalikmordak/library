package controllers;

import db.Database;
import entities.Genre;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@Named
@ApplicationScoped
public class GenreController {
    private List<Genre> genreList = new ArrayList<>();

    private ArrayList<Genre> getGenres() {
        genreList = Database.getInstance().getAllGenres();
        return (ArrayList<Genre>) genreList;
    }

    public ArrayList<Genre> getGenreList() {
        if (!genreList.isEmpty()) {
            return (ArrayList<Genre>) genreList;
        } else return getGenres();
    }

}
