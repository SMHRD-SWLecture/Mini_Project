package mini_project;

import java.sql.*;
import java.util.Scanner;
import javax.sound.sampled.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class mini_project {
    private static Connection conn;
    private static PreparedStatement psmt;
    private static ResultSet rs;
    private static Scanner sc = new Scanner(System.in);
    private static String currentUser = null;
    
    public static void main(String[] args) {
        connectDB();
        
        while (true) {
            System.out.println("=== 음악 퀴즈 게임 ===");
            System.out.println("1. 로그인");
            System.out.println("2. 회원가입");
            System.out.println("3. 게임시작");
            System.out.println("4. 랭킹보기");
            System.out.println("5. 종료");
            System.out.print("선택: ");
            
            int choice = sc.nextInt();
            sc.nextLine();
            
            switch (choice) {
                case 1:
                    login();
                    break;
                case 2:
                    register();
                    break;
                case 3:
                    if (currentUser != null) {
                        playGame();
                    } else {
                        System.out.println("로그인이 필요합니다.");
                    }
                    break;
                case 4:
                    showRanking();
                    break;
                case 5:
                    System.out.println("게임을 종료합니다.");
                    closeDB();
                    return;
            }
        }
    }

    private static void connectDB() {
        try {
            System.out.println("데이터베이스 연결 시도 중...");
            Class.forName("oracle.jdbc.driver.OracleDriver");
            System.out.println("Oracle 드라이버 로드 성공");
            
            String url = "jdbc:oracle:thin:@project-db-cgi.smhrd.com:1524:xe";
            String user = "cgi_25k_cloud2_p1_3";
            String password = "smhrd3";
            
            System.out.println("연결 정보:");
            System.out.println("URL: " + url);
            System.out.println("User: " + user);
            
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("데이터베이스 연결 성공!");
            
        } catch (ClassNotFoundException e) {
            System.out.println("Oracle JDBC 드라이버를 찾을 수 없습니다.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("데이터베이스 연결 실패!");
            System.out.println("에러 메시지: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("예상치 못한 오류 발생!");
            e.printStackTrace();
        }
    }

    private static void login() {
        System.out.println("\n=== 로그인 ===");
        System.out.print("아이디: ");
        String id = sc.nextLine();
        System.out.print("비밀번호: ");
        String pw = sc.nextLine();
        
        try {
            String query = "SELECT * FROM USERINFO WHERE ID = ? AND PW = ?";
            psmt = conn.prepareStatement(query);
            psmt.setString(1, id);
            psmt.setString(2, pw);
            rs = psmt.executeQuery();
            
            if (rs.next()) {
                currentUser = id;
                int score = rs.getInt("SCORE");
                System.out.println("로그인 성공! 현재 점수: " + score + "점");
            } else {
                System.out.println("아이디 또는 비밀번호가 일치하지 않습니다.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void register() {
        System.out.println("\n=== 회원가입 ===");
        
        while (true) {
            System.out.print("아이디 (10자 이하): ");
            String id = sc.nextLine();
            
            if (id.length() > 10) {
                System.out.println("아이디는 10자 이하여야 합니다.");
                continue;
            }
            
            System.out.print("비밀번호 (15자 이하): ");
            String pw = sc.nextLine();
            
            if (pw.length() > 15) {
                System.out.println("비밀번호는 15자 이하여야 합니다.");
                continue;
            }
            
            System.out.print("닉네임 (10자 이하): ");
            String nick = sc.nextLine();
            
            if (nick.length() > 10) {
                System.out.println("닉네임은 10자 이하여야 합니다.");
                continue;
            }
            
            try {
                String checkQuery = "SELECT COUNT(*) FROM USERINFO WHERE ID = ?";
                psmt = conn.prepareStatement(checkQuery);
                psmt.setString(1, id);
                rs = psmt.executeQuery();
                rs.next();
                
                if (rs.getInt(1) > 0) {
                    System.out.println("이미 존재하는 아이디입니다.");
                    continue;
                }
                
                String insertQuery = "INSERT INTO USERINFO (ID, PW, NICK, SCORE) VALUES (?, ?, ?, 0)";
                psmt = conn.prepareStatement(insertQuery);
                psmt.setString(1, id);
                psmt.setString(2, pw);
                psmt.setString(3, nick);
                psmt.executeUpdate();
                
                System.out.println("회원가입이 완료되었습니다!");
                break;
                
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("회원가입 중 오류가 발생했습니다.");
                break;
            }
        }
    }

    private static void playGame() {
        int totalScore = 0;
        
        try {
            // 음악 폴더 경로 설정
            String musicFolderPath = "cutMusic";
            File musicFolder = new File(musicFolderPath);
            File[] musicFiles = musicFolder.listFiles((dir, name) -> name.toLowerCase().endsWith(".mp3") || name.toLowerCase().endsWith(".wav"));
            
            if (musicFiles == null || musicFiles.length == 0) {
                System.out.println("음악 폴더에 재생 가능한 음악 파일이 없습니다.");
                return;
            }
            
            // 랜덤으로 음악 파일 선택
            int randomIndex = (int)(Math.random() * musicFiles.length);
            File selectedMusic = musicFiles[randomIndex];
            String fileName = selectedMusic.getName().substring(0, selectedMusic.getName().lastIndexOf('.'));
            
            // MUSIC 테이블에서 해당 파일의 정답 가져오기
            String query = "SELECT TITLE FROM MUSIC WHERE ROUTE LIKE ?";
            psmt = conn.prepareStatement(query);
            psmt.setString(1, "%" + fileName + "%");
            rs = psmt.executeQuery();
            
            if (!rs.next()) {
                System.out.println("해당 음악의 정보를 찾을 수 없습니다.");
                return;
            }
            
            String correctTitle = rs.getString("TITLE");
            
            int retryCount = 3;
            while (retryCount > 0) {
                System.out.println("\n=== 노래 맞추기 ===");
                System.out.println("1. 1초 듣기 (10점)");
                System.out.println("2. 3초 듣기 (5점)");
                System.out.println("3. 5초 듣기 (3점)");
                System.out.println("4. 10초 듣기 (1점)");
                System.out.print("선택: ");
                
                int choice = sc.nextInt();
                sc.nextLine();
                
                int playTime = 0;
                int possibleScore = 0;
                
                switch (choice) {
                    case 1: playTime = 1000; possibleScore = 10; break;
                    case 2: playTime = 3000; possibleScore = 5; break;
                    case 3: playTime = 5000; possibleScore = 3; break;
                    case 4: playTime = 10000; possibleScore = 1; break;
                    default: continue;
                }
                
                playMusic(selectedMusic.getAbsolutePath(), playTime);
                
                System.out.print("정답을 입력하세요 (30초 제한): ");
                
                String userAnswer = "";
                final String[] userAnswerHolder = {""};
                Thread inputThread = new Thread(() -> {
                    try {
                        userAnswerHolder[0] = sc.nextLine().trim();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                inputThread.start();
                
                try {
                    inputThread.join(30000);
                    if (inputThread.isAlive()) {
                        inputThread.interrupt();
                        System.out.println("\n시간 초과!");
                        retryCount--;
                        continue;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                
                userAnswer = userAnswerHolder[0];
                
                // 공백을 제거하고 비교
                String userAnswerNoSpace = userAnswer.replaceAll("\\s+", "");
                String correctTitleNoSpace = correctTitle.replaceAll("\\s+", "");
                
                if (userAnswerNoSpace.equals(correctTitleNoSpace)) {
                    System.out.println("정답입니다! +" + possibleScore + "점");
                    totalScore += possibleScore;
                    break;
                } else {
                    retryCount--;
                    if (retryCount > 0) {
                        System.out.println("틀렸습니다. 남은 기회: " + retryCount);
                    } else {
                        System.out.println("틀렸습니다. 정답은 '" + correctTitle + "' 였습니다.");
                    }
                }
            }
            
            updateScore(currentUser, totalScore);
            System.out.println("\n게임 종료! 총 점수: " + totalScore);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void updateScore(String id, int newScore) {
        try {
            String getScoreQuery = "SELECT SCORE FROM USERINFO WHERE ID = ?";
            psmt = conn.prepareStatement(getScoreQuery);
            psmt.setString(1, id);
            rs = psmt.executeQuery();
            
            if (rs.next()) {
                int currentScore = rs.getInt("SCORE");
                int totalScore = currentScore + newScore;
                
                String updateQuery = "UPDATE USERINFO SET SCORE = ? WHERE ID = ?";
                psmt = conn.prepareStatement(updateQuery);
                psmt.setInt(1, totalScore);
                psmt.setString(2, id);
                psmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void showRanking() {
        System.out.println("\n=== 랭킹 TOP 10 ===");
        try {
                String query = "SELECT * FROM (SELECT ID, NICK, SCORE FROM USERINFO ORDER BY SCORE DESC) WHERE ROWNUM <= 10";
            psmt = conn.prepareStatement(query);
            rs = psmt.executeQuery();
            
            int rank = 1;
            while (rs.next()) {
                String id = rs.getString("ID");
                String nick = rs.getString("NICK");
                int score = rs.getInt("SCORE");
                System.out.printf("%d위: %s(%s) - %d점\n", rank++, nick, id, score);
            }
            
            if (rank == 1) {
                System.out.println("등록된 점수가 없습니다.");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void playMusic(String filePath, int duration) {
        try {
            File musicFile = new File(filePath);
            if (filePath.toLowerCase().endsWith(".mp3")) {
                // MP3 파일 재생 (afplay 사용)
                ProcessBuilder pb = new ProcessBuilder("afplay", filePath);
                Process process = pb.start();
                Thread.sleep(duration);
                process.destroy();
            } else if (filePath.toLowerCase().endsWith(".wav")) {
                // WAV 파일 재생
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(musicFile);
                Clip clip = AudioSystem.getClip();
                clip.open(audioStream);
                clip.start();
                
                Thread.sleep(duration);
                
                clip.stop();
                clip.close();
                audioStream.close();
            } else {
                System.out.println("지원하지 않는 오디오 파일 형식입니다.");
            }
        } catch (Exception e) {
            System.out.println("음악 재생 중 오류가 발생했습니다: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void closeDB() {
        try {
            if (rs != null) rs.close();
            if (psmt != null) psmt.close();
            if (conn != null) conn.close();
            sc.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}