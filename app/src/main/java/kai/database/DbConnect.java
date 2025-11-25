package kai.database;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbConnect {
    private static final String URL = "jdbc:mysql://localhost:3306/db_kereta?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "";
  
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        Connection conn = getConnection();
        if (conn != null) {
            System.out.println("CONNECTED!");
        } else {
            System.out.println("FAILED!");
        }
    }
}