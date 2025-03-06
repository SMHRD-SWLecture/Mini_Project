package mini_project;

import java.util.ArrayList;

public class View {
    public void showMessage(String message) {
        System.out.println("\n" + message);
    }

    public void showMainMenu() {
        System.out.println("\n=== 음악 퀴즈 게임 ===");
        System.out.println("1. 로그인");
        System.out.println("2. 회원가입");
        System.out.println("3. 종료");
        System.out.print("선택: ");
    }

    public void showGameMenu() {
        System.out.println("\n=== 게임 메뉴 ===");
        System.out.println("1. 게임 시작");
        System.out.println("2. 랭킹 보기");
        System.out.println("3. 로그아웃");
        System.out.print("선택: ");
    }

    public void showGameDifficultyMenu() {
        System.out.println("\n=== 난이도 선택 ===");
        System.out.println("1. 쉬움 (1초 재생, 10점)");
        System.out.println("2. 보통 (3초 재생, 5점)");
        System.out.println("3. 어려움 (5초 재생, 3점)");
        System.out.println("4. 매우 어려움 (10초 재생, 1점)");
        System.out.println("5. 뒤로 가기");
        System.out.print("선택: ");
    }

    public void showLoginPrompt() {
        System.out.print("\n아이디를 입력하세요: ");
    }

    public void showPasswordPrompt() {
        System.out.print("비밀번호를 입력하세요: ");
    }

    public void showNickPrompt() {
        System.out.print("닉네임을 입력하세요: ");
    }

    public void showRegisterInfo() {
        System.out.println("\n=== 회원가입 ===");
        System.out.println("아이디는 10자, 비밀번호는 15자, 닉네임은 10자까지 가능합니다.");
    }

    public void showRanking(ArrayList<Model.User> ranking) {
        System.out.println("\n=== 랭킹 ===");
        if (ranking.isEmpty()) {
            System.out.println("아직 랭킹 정보가 없습니다.");
            return;
        }
        
        for (int i = 0; i < ranking.size(); i++) {
            Model.User user = ranking.get(i);
            System.out.printf("%d. %s (%s) - %d점\n", 
                i + 1, user.getNick(), user.getId(), user.getScore());
        }
    }

    public void showGameStatus(String status) {
        System.out.println("\n" + status);
    }
} 