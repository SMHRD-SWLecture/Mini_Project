package mini_project;

import java.io.File;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
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
    	System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓\r\n"
    			+ "┃                                                             ┃\r\n"
    			+ "┃   █▀▄▀█ █░█ █▀ █ █▀▀   █▀█ █░█ █ ▀█   █▀▀ ▄▀█ █▀▄▀█ █▀▀     ┃\r\n"
    			+ "┃   █░▀░█ █▄█ ▄█ █ █▄▄   ▀▀█ █▄█ █ █▄   █▄█ █▀█ █░▀░█ ██▄     ┃\r\n"
    			+ "┃                                                             ┃\r\n"
    			+ "┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛\r\n"
    			+ "      ♪        ♫        ♪        ♫        ♪        ♫        ♪");
    	try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
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
                    	System.out.println();
                    	System.out.println();
            	    	System.out.println();
            	    	System.out.println();
            	    	System.out.println();
            	    	System.out.println();
            	    	System.out.println();
            	    	System.out.println();
            	    	System.out.println();
            	    	System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            	    	System.out.println("                                               ");
            	    	System.out.println("                                               ");
            	    	 System.out.println("       █▀▀ █▀█ █▀█ █▀▄   █▄▄ █▄█ █▀▀ █");
            	         System.out.println("       █▄█ █▄█ █▄█ █▄▀   █▄█ ░█░ ██▄ ▄");
            	    	System.out.println("                                              ");
            	    	System.out.println("                                               ");
            	    	System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            	    	System.out.println("    ♪       ♫       ♪       ♫       ♪       ♫   ");
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
            	System.out.println();
            	System.out.println();
            	System.out.println();
            	System.out.println();
            	System.out.println();
            	System.out.println();
            	System.out.println();
            	System.out.println();
            	System.out.println();
                System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
                System.out.println("                                                    ");
                System.out.println("          █░█░█ █▀▀ █░░ █▀▀ █▀█ █▀▄▀█ █▀▀ █         ");
                System.out.println("          ▀▄▀▄▀ ██▄ █▄▄ █▄▄ █▄█ █░▀░█ ██▄ ▄         ");
                System.out.println("                                                    ");
                System.out.printf("       ₊ ˚ ₊ .:･˚"+model.getCurrentUserNick()+"님, 환영합니다!"+"₊ ˚ ‧₊ .:･˚₊ *˚        %n");
                System.out.println("                                                    ");
                System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
                System.out.println("    ♪       ♫       ♪       ♫       ♪       ♫       ");
            	System.out.println();
            	System.out.println();
            	System.out.println();
            	System.out.println();
            	System.out.println();
            	try {
        			TimeUnit.SECONDS.sleep(1);
        		} catch (InterruptedException e) {
        			e.printStackTrace();
        		}
                gameMenu();
            } else {
            	System.out.println();
            	System.out.println();
            	System.out.println();
            	System.out.println();
            	System.out.println();
            	System.out.println();
            	System.out.println();
            	System.out.println();
            	System.out.println();
                System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
                System.out.println("┃                                                    ┃");
                System.out.println("┃               █▀▀ ▄▀█ █ █░░ █▀▀ █▀▄ █              ┃");
                System.out.println("┃               █▀░ █▀█ █ █▄▄ ██▄ █▄▀ ▄              ┃");
                System.out.println("┃                                                    ┃");
                System.out.println("┃            아이디 또는 비밀번호가 일치하지 않습니다            ┃");
                System.out.println("┃                                                    ┃");
                System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
                System.out.println("    ♪       ♫       ♪       ♫       ♪       ♫       ");
            	try {
        			TimeUnit.SECONDS.sleep(1);
        		} catch (InterruptedException e) {
        			e.printStackTrace();
        		}
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
            	System.out.println();
            	System.out.println();
            	System.out.println();
            	System.out.println();
            	System.out.println();
            	System.out.println();
            	System.out.println();
            	System.out.println();
            	System.out.println();
            	System.out.println();
            	System.out.println();
            	System.out.println();
            	System.out.println();
            	System.out.println();
            	System.out.println();
            	System.out.println();
            	System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
            	System.out.println("┃                                                    ┃");
            	System.out.println("┃              █▀ █░█ █▀▀ █▀▀ █▀▀ █▀ █▀              ┃");
            	System.out.println("┃              ▄█ █▄█ █▄▄ █▄▄ ██▄ ▄█ ▄█              ┃");
            	System.out.println("┃                                                    ┃");
            	System.out.println("┃                 회원가입이 완료되었습니다!                 ┃");
            	System.out.println("┃                                                    ┃");
            	System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
            	System.out.println("    ♪       ♫       ♪       ♫       ♪       ♫       ");
                try {
        			TimeUnit.SECONDS.sleep(1);
        		} catch (InterruptedException e) {
        			e.printStackTrace();
        		}
            } else {
            	System.out.println();
            	System.out.println();
            	System.out.println();
            	System.out.println();
            	System.out.println();
            	System.out.println();
            	System.out.println();
            	System.out.println();
            	System.out.println();
            	System.out.println();
            	System.out.println();
            	System.out.println();
            	System.out.println();
            	System.out.println();
            	System.out.println();
            	System.out.println();
                System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
                System.out.println("┃                                                    ┃");
                System.out.println("┃               █▀▀ ▄▀█ █ █░░ █▀▀ █▀▄ █              ┃");
                System.out.println("┃               █▀░ █▀█ █ █▄▄ ██▄ █▄▀ ▄              ┃");
                System.out.println("┃                                                    ┃");
                System.out.println("┃                 이미 존재하는 아이디입니다                 ┃");
                System.out.println("┃                                                    ┃");
                System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
                System.out.println("    ♪       ♫       ♪       ♫       ♪       ♫       ");
                try {
        			TimeUnit.SECONDS.sleep(1);
        		} catch (InterruptedException e) {
        			e.printStackTrace();
        		}
            }
        } catch (Exception e) {
            view.showMessage("회원가입 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    private void gameMenu() {
        while (true) {
        	System.out.println();
	    	System.out.println();
	    	System.out.println();
	    	System.out.println();
	    	System.out.println();
	    	System.out.println();
	    	System.out.println();
	    	System.out.println();
	    	System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
	    	System.out.println("                                               ");
	    	System.out.println("                                               ");
	    	System.out.println("     █▀▀ ▄▀█ █▀▄▀█ █▀▀   █▀▄▀█ █▀▀ █▄░█ █░█  ");
	    	System.out.println("     █▄█ █▀█ █░▀░█ ██▄   █░▀░█ ██▄ █░▀█ █▄█  ");
	    	System.out.println("                                              ");
	    	System.out.println("                                              ");
	    	System.out.println("                                              ");
	    	System.out.println("                  1. 게임시작                       ");
	    	System.out.println("                  2. 랭킹                      ");
	    	System.out.println("                  3. 로그아웃                         ");
	    	System.out.println("                                               ");
	    	System.out.println("                                               ");
	    	System.out.println("                                               ");
	    	System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
	    	System.out.println("    ♪       ♫       ♪       ♫       ♪       ♫   ");
	    	System.out.print("선택:");

            try {
//                view.showGameMenu();
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
                        showMusicPlayingScreen();
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
                        	System.out.println();
                        	System.out.println();
                        	System.out.println();
                        	System.out.println();
                        	System.out.println();
                        	System.out.println();
                        	System.out.println();
                        	System.out.println("██████╗      ██████╗ ██████╗ ██████╗ ██████╗ ███████╗ ██████╗████████╗     ██████╗ ");
                        	System.out.println("██╔═══██╗    ██╔════╝██╔═══██╗██╔══██╗██╔══██╗██╔════╝██╔════╝╚══██╔══╝    ██╔═══██╗");
                        	System.out.println("██║   ██║    ██║     ██║   ██║██████╔╝██████╔╝█████╗  ██║        ██║       ██║   ██║");
                        	System.out.println("██║   ██║    ██║     ██║   ██║██╔══██╗██╔══██╗██╔══╝  ██║        ██║       ██║   ██║");
                        	System.out.println("╚██████╔╝    ╚██████╗╚██████╔╝██║  ██║██║  ██║███████╗╚██████╗   ██║       ╚██████╔╝");
                        	System.out.println(" ╚═════╝      ╚═════╝ ╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝ ╚═════╝   ╚═╝        ╚═════╝ ");
                            view.showMessage("                        정답입니다! +" + gameChoice.score + "점");
                            totalScore += gameChoice.score;
                            correct = true;
                        } else {
                        	System.out.println();
                        	System.out.println();
                        	System.out.println();
                        	System.out.println();
                        	System.out.println();
                        	System.out.println();
                        	System.out.println();
                        	System.out.println("██╗  ██╗    ██╗    ██╗██████╗  ██████╗ ███╗   ██╗ ██████╗     ██╗  ██╗");
                        	System.out.println("╚██╗██╔╝    ██║    ██║██╔══██╗██╔═══██╗████╗  ██║██╔════╝     ╚██╗██╔╝");
                        	System.out.println(" ╚███╔╝     ██║ █╗ ██║██████╔╝██║   ██║██╔██╗ ██║██║  ███╗     ╚███╔╝ ");
                        	System.out.println(" ██╔██╗     ██║███╗██║██╔══██╗██║   ██║██║╚██╗██║██║   ██║     ██╔██╗ ");
                        	System.out.println("██╔╝ ██╗    ╚███╔███╔╝██║  ██║╚██████╔╝██║ ╚████║╚██████╔╝    ██╔╝ ██╗");
                        	System.out.println("╚═╝  ╚═╝     ╚══╝╚══╝ ╚═╝  ╚═╝ ╚═════╝ ╚═╝  ╚═══╝ ╚═════╝     ╚═╝  ╚═╝");
                        	System.out.printf("                           남은 기회: %d회\n", retryCount);
                        	retryCount--;
                            try {
                    			TimeUnit.SECONDS.sleep(1);
                    		} catch (InterruptedException e) {
                    			e.printStackTrace();
                    		}
                        }
                    } catch (Exception e) {
                        retryCount--;
                    }
                }

                if (!correct) {
                    view.showMessage("정답은 '" + correctTitle + "' 입니다.");
                }

                System.out.print("\n다음 문제로 넘어가시겠습니까? (Y/N): ");
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
    public void showMusicPlayingScreen() {
    	System.out.println();
    	System.out.println();
    	System.out.println();
    	System.out.println();
    	System.out.println();
    	System.out.println();
    	System.out.println();
    	System.out.println();
        System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
        System.out.println("┃                                                    ┃");
        System.out.println("┃     █▄░█ █▀█ █░█░█   █▀█ █░░ ▄▀█ █▄█ █ █▄░█ █▀▀    ┃");
        System.out.println("┃     █░▀█ █▄█ ▀▄▀▄▀   █▀▀ █▄▄ █▀█ ░█░ █ █░▀█ █▄█    ┃");
        System.out.println("┃                                                    ┃");
        System.out.println("┃                                                    ┃");
        System.out.println("┃                   ♫♪♫ ♪♫♪ ♫♪♫                      ┃");
        System.out.println("┃                   ▶ 재생 중...                       ┃");
        System.out.println("┃                                                    ┃");
        System.out.println("┃                                                    ┃");
        System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
        System.out.println();
        System.out.println();
        System.out.println();
    }
} 