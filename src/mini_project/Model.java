package mini_project;

import java.io.File;
import java.util.ArrayList;
import javax.sound.sampled.*;
import java.sql.*;
import java.util.concurrent.TimeUnit;

public class Model {
    private static final String DB_URL = "jdbc:oracle:thin:@project-db-cgi.smhrd.com:1524:xe";
    private static final String DB_USER = "cgi_25k_cloud2_p1_3";
    private static final String DB_PASSWORD = "smhrd3";
    
    private String currentUser;
    private String os;
    private Connection conn;
    private PreparedStatement psmt;
    private ResultSet rs;

    // 내부 클래스로 User 데이터 구조 정의
    public static class User {
        private final String id;
        private final String nick;
        private int score;

        public User(String id, String nick, int score) {
            this.id = id;
            this.nick = nick;
            this.score = score;
        }

        public String getId() { return id; }
        public String getNick() { return nick; }
        public int getScore() { return score; }
        public void setScore(int score) { this.score = score; }
    }

    public Model() {
        currentUser = null;
        os = System.getProperty("os.name").toLowerCase();
        connectDB();
    }

    private void connectDB() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (Exception e) {
            throw new RuntimeException("데이터베이스 연결 실패: " + e.getMessage());
        }
    }

    public boolean login(String id, String pw) {
        try {
            String query = "SELECT * FROM USERINFO WHERE ID = ? AND PW = ?";
            psmt = conn.prepareStatement(query);
            psmt.setString(1, id);
            psmt.setString(2, pw);
            rs = psmt.executeQuery();
            
            if (rs.next()) {
                currentUser = id;
                return true;
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException("로그인 실패: " + e.getMessage());
        }
    }

    public boolean register(String id, String pw, String nick) {
        if (id.length() > 10 || pw.length() > 15 || nick.length() > 10) {
            return false;
        }
        
        try {
            if (isUserExists(id)) {
                return false;
            }
            
            String insertQuery = "INSERT INTO USERINFO (ID, PW, NICK, SCORE) VALUES (?, ?, ?, 0)";
            psmt = conn.prepareStatement(insertQuery);
            psmt.setString(1, id);
            psmt.setString(2, pw);
            psmt.setString(3, nick);
            psmt.executeUpdate();
            
            return true;
        } catch (SQLException e) {
            throw new RuntimeException("회원가입 실패: " + e.getMessage());
        }
    }

    private boolean isUserExists(String id) throws SQLException {
        String checkQuery = "SELECT COUNT(*) FROM USERINFO WHERE ID = ?";
        psmt = conn.prepareStatement(checkQuery);
        psmt.setString(1, id);
        rs = psmt.executeQuery();
        rs.next();
        return rs.getInt(1) > 0;
    }

    public void updateScore(int score) {
        try {
            String getScoreQuery = "SELECT SCORE FROM USERINFO WHERE ID = ?";
            psmt = conn.prepareStatement(getScoreQuery);
            psmt.setString(1, currentUser);
            rs = psmt.executeQuery();
            
            if (rs.next()) {
                int currentScore = rs.getInt("SCORE");
                int totalScore = currentScore + score;
                
                String updateQuery = "UPDATE USERINFO SET SCORE = ? WHERE ID = ?";
                psmt = conn.prepareStatement(updateQuery);
                psmt.setInt(1, totalScore);
                psmt.setString(2, currentUser);
                psmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException("점수 업데이트 실패: " + e.getMessage());
        }
    }

    public ArrayList<User> getRanking() {
        ArrayList<User> ranking = new ArrayList<>();
        try {
            String query = "SELECT * FROM (SELECT ID, NICK, SCORE FROM USERINFO ORDER BY SCORE DESC) WHERE ROWNUM <= 10";
            psmt = conn.prepareStatement(query);
            rs = psmt.executeQuery();
            
            while (rs.next()) {
                ranking.add(new User(
                    rs.getString("ID"),
                    rs.getString("NICK"),
                    rs.getInt("SCORE")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("랭킹 조회 실패: " + e.getMessage());
        }
        return ranking;
    }

    public String getMusicTitle(String fileName) {
        try {
            String query = "SELECT TITLE FROM MUSIC WHERE ROUTE LIKE ?";
            psmt = conn.prepareStatement(query);
            psmt.setString(1, "%" + fileName + "%");
            rs = psmt.executeQuery();
            
            return rs.next() ? rs.getString("TITLE") : null;
        } catch (SQLException e) {
            throw new RuntimeException("음악 정보 조회 실패: " + e.getMessage());
        }
    }

    public void playMusic(String filePath, int duration) {
        File musicFile = new File(filePath);
        String extension = filePath.toLowerCase();
        
        try {
            if (extension.endsWith(".mp3")) {
                playMP3(filePath, duration);
            } else if (extension.endsWith(".wav")) {
                playWAV(musicFile, duration);
            } else {
                throw new IllegalArgumentException("지원하지 않는 파일 형식입니다.");
            }
        } catch (Exception e) {
            throw new RuntimeException("음악 재생 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    private void playMP3(String filePath, int duration) throws Exception {
        Process process = null;
        try {
            ProcessBuilder pb = os.contains("mac") 
                ? new ProcessBuilder("afplay", filePath)
                : new ProcessBuilder("cmd", "/c", "start", "/min", "wmplayer", filePath);
            
            process = pb.start();
            Thread.sleep(duration);
        } finally {
            if (process != null) {
                process.destroy();
                try {
                    process.waitFor(1, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    process.destroyForcibly();
                }
            }
        }
    }

    private void playWAV(File musicFile, int duration) throws Exception {
        AudioInputStream audioStream = null;
        Clip clip = null;
        try {
            audioStream = AudioSystem.getAudioInputStream(musicFile);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
            Thread.sleep(duration);
        } finally {
            if (clip != null) {
                clip.stop();
                clip.close();
            }
            if (audioStream != null) {
                audioStream.close();
            }
        }
    }

    public String getCurrentUser() {
        return currentUser;
    }

    public String getCurrentUserNick() {
        try {
            String query = "SELECT NICK FROM USERINFO WHERE ID = ?";
            psmt = conn.prepareStatement(query);
            psmt.setString(1, currentUser);
            rs = psmt.executeQuery();
            
            if (rs.next()) {
                return rs.getString("NICK");
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("사용자 정보 조회 실패: " + e.getMessage());
        }
    }

    public void closeDB() {
        try {
            if (rs != null) {
                rs.close();
                rs = null;
            }
            if (psmt != null) {
                psmt.close();
                psmt = null;
            }
            if (conn != null) {
                conn.close();
                conn = null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("데이터베이스 연결 종료 실패: " + e.getMessage());
        }
    }

    public void logout() {
        currentUser = null;
    }
} 