//package company;
//
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//
//
//public class UserRepository implements UserDao {
//    private String username;
//    private String pass;
//
//    public UserRepository() {
//
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public void setPass(String pass) {
//        this.pass = pass;
//    }
//
//    private UserRepository extractUserFromResultSet(ResultSet result) throws SQLException {
//        UserRepository user = new UserRepository();
//
//        user.setUsername(result.getString("Username"));
//        user.setPass(result.getString("pass"));
//        return user;
//    }
//
//
//    @Override
//    public UserRepository getUser(String username) {
//        try {
//            Statement stmt = DBConnection.getInstance().getConn("dburl", "user", "pass").createStatement();
//            ResultSet result = stmt.executeQuery("select * from user");
//
//            if (result.next()) {
//                return extractUserFromResultSet(result);
//
//            }
//
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
//
//        return null;
//    }
//}
//
