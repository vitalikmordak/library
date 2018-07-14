package beans;

import db.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AuthorList {
    private List<Author> authorList = new ArrayList<>();
    private Connection connection;
    private Statement stat;
    private ResultSet rs;

    private ArrayList<Author> getAuthors() {
        try {
            connection = Database.getConnection();

            stat = connection.createStatement();
            rs = stat.executeQuery("select * from author order by fio");
            while (rs.next()) {
                Author author = new Author();
                author.setName(rs.getString("fio"));
                author.setId(rs.getLong("id"));
                authorList.add(author);
            }
        } catch (SQLException e) {
            Logger.getLogger(AuthorList.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (stat != null) stat.close();
                if (connection != null) connection.close();
                if (rs != null) rs.close();
            } catch (SQLException e) {
                Logger.getLogger(AuthorList.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        return (ArrayList<Author>) authorList;
    }

    public ArrayList<Author> getAuthorList() {
        if (!authorList.isEmpty()) {
            return (ArrayList<Author>) authorList;
        } else return getAuthors();
    }

}
