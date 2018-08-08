package db;

import sun.reflect.Reflection;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database {
    private static Connection connection;

    public static Connection getConnection() {
        try {
            InitialContext ic = new InitialContext();
            DataSource ds = (DataSource) ic.lookup("java:comp/env/jdbc/library");
            connection = ds.getConnection();

        } catch (SQLException | NamingException e) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, e);
        }
        return connection;
    }

    // Close connection to DB
    public static void closeConnections(Connection connection, Statement stat, ResultSet rs) {
        try {
            if (stat != null) stat.close();
            if (connection != null) connection.close();
            if (rs != null) rs.close();
        } catch (SQLException e) {
            Logger.getLogger(Reflection.getCallerClass().getName()).log(Level.SEVERE, null, e);
        }
    }
}
