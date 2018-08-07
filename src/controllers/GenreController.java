package controllers;

import beans.Genre;
import db.Database;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@Eager
@ApplicationScoped
public class GenreController {
    private final List<Genre> genreList = new ArrayList<>();
    private Connection connection;
    private Statement stat;
    private ResultSet rs;

    private ArrayList<Genre> getGenres() {
        try {
            connection = Database.getConnection();

            stat = connection.createStatement();
            rs = stat.executeQuery("select * from genre order by name");
            while (rs.next()) {
                Genre genre = new Genre();
                genre.setName(rs.getString("name"));
                genre.setId(rs.getLong("id"));
                genreList.add(genre);
            }
        } catch (SQLException e) {
            Logger.getLogger(GenreController.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (stat != null) stat.close();
                if (connection != null) connection.close();
                if (rs != null) rs.close();
            } catch (SQLException e) {
                Logger.getLogger(GenreController.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        return (ArrayList<Genre>) genreList;
    }

    public ArrayList<Genre> getGenreList() {
        if (!genreList.isEmpty()) {
            return (ArrayList<Genre>) genreList;
        } else return getGenres();
    }

}
