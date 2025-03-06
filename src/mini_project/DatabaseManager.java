package mini_project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:oracle:thin:@project-db-cgi.smhrd.com:1524:xe";
    private static final String DB_USER = "cgi_25k_cloud2_p1_3";
    private static final String DB_PASSWORD = "smhrd3";
    
    private static DatabaseManager instance;
    private Connection conn;
    
    private DatabaseManager() {
        connectDB();
    }
    
    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }
    
    private void connectDB() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (Exception e) {
            throw new RuntimeException("데이터베이스 연결 실패: " + e.getMessage());
        }
    }
    
    public PreparedStatement prepareStatement(String query) throws SQLException {
        return conn.prepareStatement(query);
    }
    
    public void closeResources(ResultSet rs, PreparedStatement psmt) {
        try {
            if (rs != null) rs.close();
            if (psmt != null) psmt.close();
        } catch (SQLException e) {
            throw new RuntimeException("리소스 해제 실패: " + e.getMessage());
        }
    }
    
    public void closeConnection() {
        try {
            if (conn != null) {
                conn.close();
                conn = null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("데이터베이스 연결 종료 실패: " + e.getMessage());
        }
    }
} 