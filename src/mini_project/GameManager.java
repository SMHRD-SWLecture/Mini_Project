package mini_project;

import java.io.File;
import java.io.FileInputStream;
import java.util.Random;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.decoder.JavaLayerException;

public class GameManager {
    private static final String MUSIC_FOLDER = "cutMusic";
    private Model model;
    private static AdvancedPlayer player;
    private static Thread playerThread;
    
    public GameManager(Model model) {
        this.model = model;
    }
    
    public File[] getMusicFiles() {
        File musicFolder = new File(MUSIC_FOLDER);
        if (!musicFolder.exists() || !musicFolder.isDirectory()) {
            return null;
        }
        
        return musicFolder.listFiles((dir, name) -> name.toLowerCase().endsWith(".mp3"));
    }
    
    public File selectRandomMusic(File[] musicFiles) {
        return musicFiles[new Random().nextInt(musicFiles.length)];
    }
    
    public void playMusic(String filePath, int duration) {
        if (!filePath.toLowerCase().endsWith(".mp3")) {
            throw new RuntimeException("MP3 파일만 지원합니다.");
        }
        
        try {
            stopCurrentMusic();
            
            FileInputStream fis = new FileInputStream(filePath);
            player = new AdvancedPlayer(fis);
            
            playerThread = new Thread(() -> {
                try {
                    player.play();
                } catch (JavaLayerException e) {
                    throw new RuntimeException("음악 재생 실패: " + e.getMessage());
                }
            });
            
            playerThread.start();
            Thread.sleep(duration);
            stopCurrentMusic();
            
        } catch (Exception e) {
            throw new RuntimeException("음악 재생 실패: " + e.getMessage());
        }
    }
    
    private void stopCurrentMusic() {
        try {
            if (player != null) {
                player.close();
                if (playerThread != null) {
                    playerThread.interrupt();
                }
                player = null;
                playerThread = null;
            }
        } catch (Exception e) {
            // 무시
        }
    }
    
    public boolean checkAnswer(String userAnswer, String correctTitle) {
        String normalizedUserAnswer = userAnswer.trim().replaceAll("\\s+", "").toLowerCase();
        String normalizedCorrectTitle = correctTitle.trim().replaceAll("\\s+", "").toLowerCase();
        return normalizedUserAnswer.equals(normalizedCorrectTitle);
    }
    
    public static class GameChoice {
        public final int playTime;
        public final int score;
        
        public GameChoice(int playTime, int score) {
            this.playTime = playTime;
            this.score = score;
        }
    }
    
    public GameChoice getGameChoice(int choice) {
        switch (choice) {
            case 1: return new GameChoice(1, 10);
            case 2: return new GameChoice(3, 5);
            case 3: return new GameChoice(5, 3);
            case 4: return new GameChoice(10, 1);
            default: return null;
        }
    }
} 