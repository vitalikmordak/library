package dao;

import db.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    public static boolean login(String user, String password) {
        Connection conn = null;
        PreparedStatement stat = null;
        try {
            conn = Database.getConnection();
            stat = conn.prepareStatement("select  username, password from users where username=? and password=?");

            stat.setString(1, user);
            stat.setString(2, password);

            ResultSet rs = stat.executeQuery();
            if (rs.next()) return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            Database.closeConnections(conn, stat, null);
        }
        return false;
    }
}
