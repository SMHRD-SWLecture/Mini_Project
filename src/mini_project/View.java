package mini_project;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class View {
    public void showMessage(String message) {
        System.out.println("\n" + message);
    }

    public void showMainMenu() {
    	System.out.println();
    	System.out.println();
    	System.out.println();
    	System.out.println();
    	System.out.println();
    	System.out.println();
    	System.out.println();
    	System.out.println();
    	System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
    	System.out.println("         ♪    └[∵┌]└[ ∵ ]┘[┐∵]┘   ♫             ");
    	System.out.println("                                               ");
    	System.out.println("       █▀▄▀█ █░█ █▀ █ █▀▀   █▀█ █░█ █ ▀█      ");
    	System.out.println("       █░▀░█ █▄█ ▄█ █ █▄▄   ▀▀█ █▄█ █ █▄      ");
    	System.out.println("                                              ");
    	System.out.println("                                              ");
    	System.out.println("                                              ");
    	System.out.println("                  1. 로그인                       ");
    	System.out.println("                  2. 회원가입                      ");
    	System.out.println("                  3. 종료                         ");
    	System.out.println("                                               ");
    	System.out.println("                                               ");
    	System.out.println("                                               ");
    	System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
    	System.out.println("    ♪       ♫       ♪       ♫       ♪       ♫   ");
    	System.out.print("선택:");
    }

    public void showGameMenu() {
        System.out.println("\n=== 게임 메뉴 ===");
        System.out.println("1. 게임 시작");
        System.out.println("2. 랭킹 보기");
        System.out.println("3. 로그아웃");
        System.out.print("선택: ");
    }

    public void showGameDifficultyMenu() {
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
    	System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
    	System.out.println("                      ");
    	System.out.println("                                               ");
    	System.out.println("  █▀▀ █░█ █▀█ █▀█ █▀ █▀▀   █░░ █▀▀ █░█ █▀▀ █░░ ");
    	System.out.println("  █▄▄ █▀█ █▄█ █▄█ ▄█ ██▄   █▄▄ ██▄ ▀▄▀ ██▄ █▄▄ ");
    	System.out.println("                                              ");
    	System.out.println("                       🎧 ");
    	System.out.println("                                              ");
        System.out.println("            1. 매우 어려움 (1초 재생, 10점)");
        System.out.println("            2. 어려움 (3초 재생, 5점)");
        System.out.println("            3. 보통 (5초 재생, 3점)");
        System.out.println("            4. 쉬움 (10초 재생, 1점)");
    	System.out.println("                                               ");
    	System.out.println("                                               ");
    	System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
    	System.out.println("    ♪       ♫       ♪       ♫       ♪       ♫   ");
    	System.out.print("선택:");
    }

    public void showLoginPrompt() {
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
        System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
        System.out.println("┃                                               ┃");
        System.out.println("┃        █░█░█ █▀▀ █░░ █▀▀ █▀█ █▀▄▀█ █▀▀ █      ┃");
        System.out.println("┃        ▀▄▀▄▀ ██▄ █▄▄ █▄▄ █▄█ █░▀░█ ██▄ ▄      ┃");
        System.out.println("┃                                               ┃");
        System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
        System.out.print("                   아이디: ");
    }

    public void showPasswordPrompt() {
    	System.out.print("                  비밀번호: ");
    	}

    public void showNickPrompt() {
        System.out.print("                  닉네임: ");
    }

    public void showRegisterInfo() {
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
        System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
        System.out.println("┃                                               ┃");
        System.out.println("┃          █▀█ █▀▀ █▀▀ █ █▀ ▀█▀ █▀▀ █▀█         ┃");
        System.out.println("┃          █▀▄ ██▄ █▄█ █ ▄█ ░█░ ██▄ █▀▄         ┃");
        System.out.println("┃                                               ┃");
        System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
        System.out.println("아이디는 10자, 비밀번호는 15자, 닉네임은 10자까지 가능합니다.");
        try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    }

    public void showRanking(ArrayList<Model.User> ranking) {
    	System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
    	System.out.println(" ");
    	System.out.println(" ");
    	System.out.println("        █▀█ ▄▀█ █▄░█ █▄▀ █ █▄░█ █▀▀ ");
    	System.out.println("        █▀▄ █▀█ █░▀█ █░█ █ █░▀█ █▄█ ");
    	System.out.println(" ");
        if (ranking.isEmpty()) {
            System.out.println("아직 랭킹 정보가 없습니다.");
            return;
        }
        for (int i = 0; i < ranking.size(); i++) {
            Model.User user = ranking.get(i);
            System.out.printf("        %d. %s (%s) - %d점\n", 
                i + 1, user.getNick(), user.getId(), user.getScore());
        }
        System.out.println(" ");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("    ♪       ♫       ♪       ♫       ♪       ♫   ");
        Scanner sc= new Scanner(System.in);
        System.err.println();
        System.out.print("아무 글자를 입력하면 다음으로 넘어갑니다! : ");
        sc.next();
        
        
    }

    public void showGameStatus(String status) {
        System.out.println("\n" + status);
    }
} 