package test;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Test {
    public void check() {
        try {
            InitialContext initialContext = new InitialContext();
            DataSource dataSource = (DataSource)initialContext.lookup("java:comp/env/jdbc/library");
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM library.book");
            while(resultSet.next()) {
            System.out.println(resultSet.getString("name"));}
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
