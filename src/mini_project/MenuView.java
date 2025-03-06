package mini_project;

public class MenuView {
    public void showMainMenu() {
        System.out.println("\n=== 음악 퀴즈 게임 ===");
        System.out.println("1. 로그인");
        System.out.println("2. 회원가입");
        System.out.println("3. 종료");
        System.out.print("선택: ");
    }

    public static void main(String[] args) {
        Controller controller = new Controller();
        controller.start();
    }
} 