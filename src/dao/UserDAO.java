package dao;

public class UserDAO {
    public static boolean login(String user, String password) {
//        Connection conn = null;
//        PreparedStatement stat = null;
//        try {
//            conn = Database.getConnection();
//            stat = conn.prepareStatement("select  user_name, user_pass from users where user_name=? and user_pass=?");
//
//            stat.setString(1, user);
//            stat.setString(2, password);
//
//            ResultSet rs = stat.executeQuery();
//            if (rs.next()) return true;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        } finally {
//            Database.closeConnections(conn, stat, null);
//        }
//        return false;
        return true;
    }
}
