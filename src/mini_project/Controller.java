package mini_project;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class Controller {
    private Model model;
    private MenuView menuView;
    private GameView gameView;
    private UserView userView;
    private MessageView messageView;
    private Scanner sc;
    private static final int TIMEOUT_SECONDS = 10;
    private static final String MUSIC_FOLDER = "cutMusic";

    public Controller() {
        model = new Model();
        menuView = new MenuView();
        gameView = new GameView();
        userView = new UserView();
        messageView = new MessageView();
        sc = new Scanner(System.in);
    }

    public void start() {
        while (true) {
            try {
                menuView.showMainMenu();
                String input = sc.nextLine().trim();
                if (input.isEmpty()) {
                    messageView.showInvalidChoice();
                    continue;
                }
                
                int choice;
                try {
                    choice = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    messageView.showInvalidChoice();
                    continue;
                }

                switch (choice) {
                    case 1:
                        login();
                        break;
                    case 2:
                        register();
                        break;
                    case 3:
                        messageView.showGoodbye();
                        return;
                    default:
                        messageView.showInvalidChoice();
                }
            } catch (Exception e) {
                messageView.showError("오류가 발생했습니다. 다시 시도해주세요.");
                sc.nextLine(); // 입력 버퍼 비우기
            }
        }
    }

    private void login() {
        userView.showLoginPrompt();
        String id = sc.nextLine();
        userView.showPasswordPrompt();
        String pw = sc.nextLine();
        
        try {
            if (model.login(id, pw)) {
                String nick = model.getCurrentUserNick();
                userView.showLoginSuccess(nick);
                gameMenu();
            } else {
                userView.showLoginFailed();
            }
        } catch (RuntimeException e) {
            userView.showLoginFailed();
        }
    }

    private void register() {
        userView.showRegisterPrompt();
        String id = sc.nextLine();
        userView.showPasswordPrompt();
        String pw = sc.nextLine();
        userView.showNickPrompt();
        String nick = sc.nextLine();

        try {
            if (model.register(id, pw, nick)) {
                userView.showRegisterSuccess();
            } else {
                userView.showUserExists();
            }
        } catch (RuntimeException e) {
            userView.showUserExists();
        }
    }

    private void gameMenu() {
        while (true) {
            System.out.println("\n=== 게임 메뉴 ===");
            System.out.println("1. 게임 시작");
            System.out.println("2. 랭킹 보기");
            System.out.println("3. 로그아웃");
            System.out.print("선택: ");
            
            int choice = sc.nextInt();
            sc.nextLine(); // 개행 문자 처리

            switch (choice) {
                case 1:
                    playGame();
                    break;
                case 2:
                    showRanking();
                    break;
                case 3:
                    model.logout();
                    return;
                default:
                    messageView.showInvalidChoice();
            }
        }
    }

    private void playGame() {
        File musicFolder = new File(MUSIC_FOLDER);
        if (!musicFolder.exists() || !musicFolder.isDirectory()) {
            gameView.showNoMusicFiles();
            return;
        }

        File[] musicFiles = musicFolder.listFiles((dir, name) -> name.toLowerCase().endsWith(".mp3") || name.toLowerCase().endsWith(".wav"));
        if (musicFiles == null || musicFiles.length == 0) {
            gameView.showNoMusicFiles();
            return;
        }

        int totalScore = 0;
        ExecutorService executor = Executors.newFixedThreadPool(2);
        AtomicBoolean gameRunning = new AtomicBoolean(true);
        File lastPlayedMusic = null;  // 마지막으로 재생한 음악 파일 추적

        try {
            while (gameRunning.get()) {
                GameChoice gameChoice = getGameChoice();
                if (gameChoice == null) {
                    break;
                }

                // 새로운 랜덤 음악 선택 (이전에 재생한 음악 제외)
                File musicFile;
                do {
                    musicFile = selectRandomMusic(musicFiles);
                } while (musicFile.equals(lastPlayedMusic));
                lastPlayedMusic = musicFile;

                String fileName = musicFile.getName();
                String correctTitle = model.getMusicTitle(fileName);
                
                if (correctTitle == null) {
                    gameView.showMusicNotFound();
                    continue;
                }

                int retryCount = 2;
                boolean correct = false;
                AtomicBoolean musicPlaying = new AtomicBoolean(false);

                while (retryCount >= 0 && !correct && gameRunning.get()) {
                    try {
                        // 1. 음악 재생 (별도 스레드에서 실행)
                        System.out.println("\n음악을 재생합니다...");
                        musicPlaying.set(true);
                        
                        // final 변수로 복사
                        final File currentMusicFile = musicFile;
                        final int currentPlayTime = gameChoice.playTime;
                        
                        // 음악 재생 스레드
                        Thread musicThread = new Thread(() -> {
                            try {
                                model.playMusic(currentMusicFile.getAbsolutePath(), currentPlayTime * 1000);
                            } catch (Exception e) {
                                if (e.getMessage().contains("지원하지 않는")) {
                                    gameView.showUnsupportedFormat();
                                } else if (e.getMessage().contains("sleep interrupted")) {
                                    // 음악 재생 중단은 정상적인 상황이므로 무시
                                } else {
                                    gameView.showPlaybackError(e.getMessage());
                                }
                            } finally {
                                musicPlaying.set(false);
                            }
                        });
                        musicThread.start();
                        
                        // 음악이 시작될 때까지 잠시 대기
                        int waitCount = 0;
                        while (!musicPlaying.get() && gameRunning.get() && waitCount < 50) {
                            Thread.sleep(100);
                            waitCount++;
                        }
                        
                        if (!gameRunning.get()) {
                            break;
                        }
                        
                        if (!musicPlaying.get()) {
                            System.out.println("음악 재생에 실패했습니다. 다시 시도합니다...");
                            continue;
                        }

                        // 2. 정답 입력 받기
                        System.out.print("\n정답을 입력하세요: ");
                        String userAnswer = sc.nextLine();
                        
                        if (!gameRunning.get()) {
                            break;
                        }
                        
                        String normalizedUserAnswer = userAnswer.trim().replaceAll("\\s+", "").toLowerCase();
                        String normalizedCorrectTitle = correctTitle.trim().replaceAll("\\s+", "").toLowerCase();
                        
                        if (normalizedUserAnswer.equals(normalizedCorrectTitle)) {
                            gameView.showCorrect(gameChoice.score);
                            totalScore += gameChoice.score;
                            correct = true;
                        } else {
                            System.out.println("\n틀렸습니다!");
                            if (retryCount > 0) {
                                System.out.println("다시 시도해보세요!");
                            }
                            retryCount--;
                        }
                    } catch (Exception e) {
                        retryCount--;
                    }
                }

                if (!correct) {
                    gameView.showCorrectAnswer(correctTitle);
                }

                // 다음 문제 진행 여부 확인 (시간 제한 없음)
                System.out.print("\n다음 문제로 넘어가시겠습니까? (y/n): ");
                String continueChoice = sc.nextLine();
                if (!continueChoice.toLowerCase().startsWith("y")) {
                    gameRunning.set(false);
                    break;
                }
            }
        } finally {
            gameRunning.set(false);
            executor.shutdownNow();
            try {
                if (!executor.awaitTermination(1, TimeUnit.SECONDS)) {
                    executor.shutdownNow();
                }
            } catch (InterruptedException e) {
                executor.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }

        gameView.showGameEnd(totalScore);
        try {
            model.updateScore(totalScore);
        } catch (RuntimeException e) {
            messageView.showError("점수 업데이트 중 오류가 발생했습니다.");
        }
    }

    private File selectRandomMusic(File[] musicFiles) {
        Random random = new Random();
        return musicFiles[random.nextInt(musicFiles.length)];
    }

    private GameChoice getGameChoice() {
        gameView.showGameMenu();
        int choice = sc.nextInt();
        sc.nextLine(); // 개행 문자 처리

        switch (choice) {
            case 1: return new GameChoice(1, 10);
            case 2: return new GameChoice(3, 5);
            case 3: return new GameChoice(5, 3);
            case 4: return new GameChoice(10, 1);
            case 5: return null;
            default:
                messageView.showInvalidChoice();
                return getGameChoice();
        }
    }

    private void showRanking() {
        try {
            ArrayList<Model.User> ranking = model.getRanking();
            gameView.showRanking(ranking);
        } catch (RuntimeException e) {
            messageView.showError("랭킹 정보를 가져오는 중 오류가 발생했습니다.");
        }
    }

    private static class GameChoice {
        int playTime;
        int score;

        GameChoice(int playTime, int score) {
            this.playTime = playTime;
            this.score = score;
        }
    }
} 