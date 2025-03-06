package mini_project;

import java.io.File;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class Controller {
    private Model model;
    private GameManager gameManager;
    private View view;
    private Scanner sc;

    public Controller() {
        model = new Model();
        gameManager = new GameManager(model);
        view = new View();
        sc = new Scanner(System.in);
    }

    public void start() {
        while (true) {
            try {
                view.showMainMenu();
                String input = sc.nextLine().trim();
                
                if (input.isEmpty()) {
                    view.showMessage("잘못된 선택입니다. 다시 시도해주세요.");
                    continue;
                }
                
                int choice = Integer.parseInt(input);
                switch (choice) {
                    case 1: login(); break;
                    case 2: register(); break;
                    case 3: 
                        view.showMessage("프로그램을 종료합니다. 안녕히 가세요!");
                        return;
                    default:
                        view.showMessage("잘못된 선택입니다. 다시 시도해주세요.");
                }
            } catch (NumberFormatException e) {
                view.showMessage("잘못된 선택입니다. 다시 시도해주세요.");
            } catch (Exception e) {
                view.showMessage("오류가 발생했습니다: " + e.getMessage());
            }
        }
    }

    private void login() {
        view.showLoginPrompt();
        String id = sc.nextLine();
        view.showPasswordPrompt();
        String pw = sc.nextLine();
        
        try {
            if (model.login(id, pw)) {
                view.showMessage(model.getCurrentUserNick() + "님, 환영합니다!");
                gameMenu();
            } else {
                view.showMessage("로그인에 실패했습니다. 아이디와 비밀번호를 확인해주세요.");
            }
        } catch (Exception e) {
            view.showMessage("로그인 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    private void register() {
        view.showRegisterInfo();
        view.showLoginPrompt();
        String id = sc.nextLine();
        view.showPasswordPrompt();
        String pw = sc.nextLine();
        view.showNickPrompt();
        String nick = sc.nextLine();

        try {
            if (model.register(id, pw, nick)) {
                view.showMessage("회원가입이 완료되었습니다. 로그인해주세요.");
            } else {
                view.showMessage("이미 존재하는 아이디입니다. 다른 아이디를 사용해주세요.");
            }
        } catch (Exception e) {
            view.showMessage("회원가입 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    private void gameMenu() {
        while (true) {
            try {
                view.showGameMenu();
                int choice = Integer.parseInt(sc.nextLine().trim());
                switch (choice) {
                    case 1: playGame(); break;
                    case 2: view.showRanking(model.getRanking()); break;
                    case 3: 
                        model.logout();
                        return;
                    default:
                        view.showMessage("잘못된 선택입니다. 다시 시도해주세요.");
                }
            } catch (NumberFormatException e) {
                view.showMessage("잘못된 선택입니다. 다시 시도해주세요.");
            }
        }
    }

    private void playGame() {
        File[] musicFiles = gameManager.getMusicFiles();
        if (musicFiles == null || musicFiles.length == 0) {
            view.showMessage("음악 파일을 찾을 수 없습니다.");
            return;
        }

        int totalScore = 0;
        AtomicBoolean gameRunning = new AtomicBoolean(true);
        File lastPlayedMusic = null;

        try {
            while (gameRunning.get()) {
                view.showGameDifficultyMenu();
                int choice = Integer.parseInt(sc.nextLine().trim());
                GameManager.GameChoice gameChoice = gameManager.getGameChoice(choice);
                
                if (gameChoice == null) {
                    if (choice == 5) break;
                    view.showMessage("잘못된 선택입니다. 다시 시도해주세요.");
                    continue;
                }

                File musicFile;
                do {
                    musicFile = gameManager.selectRandomMusic(musicFiles);
                } while (musicFile.equals(lastPlayedMusic));
                lastPlayedMusic = musicFile;

                String correctTitle = model.getMusicTitle(musicFile.getName());
                if (correctTitle == null) {
                    view.showMessage("음악 정보를 찾을 수 없습니다.");
                    continue;
                }

                int retryCount = 2;
                boolean correct = false;
                final File finalMusicFile = musicFile;

                while (retryCount >= 0 && !correct && gameRunning.get()) {
                    try {
                        view.showGameStatus("음악을 재생합니다...");
                        
                        Thread musicThread = new Thread(() -> {
                            try {
                                gameManager.playMusic(finalMusicFile.getAbsolutePath(), gameChoice.playTime * 1000);
                            } catch (Exception e) {
                                view.showMessage("음악 재생 중 오류가 발생했습니다: " + e.getMessage());
                            }
                        });
                        musicThread.start();

                        Thread.sleep(1000);
                        System.out.print("\n정답을 입력하세요: ");
                        String userAnswer = sc.nextLine();
                        
                        if (!gameRunning.get()) break;
                        
                        if (gameManager.checkAnswer(userAnswer, correctTitle)) {
                            view.showMessage("정답입니다! +" + gameChoice.score + "점");
                            totalScore += gameChoice.score;
                            correct = true;
                        } else {
                            view.showMessage("틀렸습니다!" + (retryCount > 0 ? " 다시 시도해보세요!" : ""));
                            retryCount--;
                        }
                    } catch (Exception e) {
                        retryCount--;
                    }
                }

                if (!correct) {
                    view.showMessage("정답은 '" + correctTitle + "' 입니다.");
                }

                System.out.print("\n다음 문제로 넘어가시겠습니까? (y/n): ");
                if (!sc.nextLine().toLowerCase().startsWith("y")) {
                    gameRunning.set(false);
                }
            }

            view.showMessage("게임 종료! 총점: " + totalScore + "점");
            model.updateScore(totalScore);
        } catch (Exception e) {
            view.showMessage("게임 진행 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new Controller().start();
    }
} 