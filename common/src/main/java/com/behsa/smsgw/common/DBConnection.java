//package company;
//
//import com.zaxxer.hikari.HikariConfig;
//import com.zaxxer.hikari.HikariDataSource;
//
//import java.sql.*;
//
//public class DBConnection {
//    private static HikariConfig config = new HikariConfig();
//    private static HikariDataSource ds;
//
//    static {
//        config.setJdbcUrl("jdbc_url");
//        config.setUsername("database_username");
//        config.setPassword("database_password");
//        config.addDataSourceProperty("cachePrepStmts", "true");
//        config.addDataSourceProperty("prepStmtCacheSize", "250");
//        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
//        ds = new HikariDataSource(config);
//    }
//
//    private static DBConnection DB_Con;
//
//    private DBConnection() {
//    }
//
//    public static DBConnection getInstance() {
//        if (DB_Con == null) {
//            DB_Con = new DBConnection();
//        }
//        return DB_Con;
//    }
//
//    public Connection getConn(String url, String user, String password) {
//        try {
//            Class.forName("oracle.jdbc.driver.OracleDriver");
//            Connection connection = DriverManager.getConnection(
//                    url, user, password);
//
//            return connection;
//
//
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//
//        return null;
//    }
//
//
//}
