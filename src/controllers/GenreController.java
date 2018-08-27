package controllers;

import db.Database;
import entities.Genre;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@Named
@Eager
@ApplicationScoped
public class GenreController {
    private List<Genre> genreList = new ArrayList<>();
//    private Connection connection;
//    private Statement stat;
//    private ResultSet rs;

    private ArrayList<Genre> getGenres() {
//        try {
//            connection = Database.getConnection();
//
//            stat = connection.createStatement();
//            rs = stat.executeQuery("select * from genre order by name");
//            while (rs.next()) {
//                Genre genre = new Genre();
//                genre.setName(rs.getString("name"));
//                genre.setId(rs.getLong("id"));
//                genreList.add(genre);
//            }
//        } catch (SQLException e) {
//            Logger.getLogger(GenreController.class.getName()).log(Level.SEVERE, null, e);
//        } finally {
//            try {
//                if (stat != null) stat.close();
//                if (connection != null) connection.close();
//                if (rs != null) rs.close();
//            } catch (SQLException e) {
//                Logger.getLogger(GenreController.class.getName()).log(Level.SEVERE, null, e);
//            }
//        }
        genreList = Database.getInstance().getAllGenres();
        return (ArrayList<Genre>) genreList;
    }

    public ArrayList<Genre> getGenreList() {
        if (!genreList.isEmpty()) {
            return (ArrayList<Genre>) genreList;
        } else return getGenres();
    }

}
