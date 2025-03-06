package mini_project;

public class UserView {
    public void showLoginMenu() {
        System.out.println("\n=== 로그인 ===");
        System.out.println("1. 로그인");
        System.out.println("2. 회원가입");
        System.out.println("3. 메인으로 돌아가기");
        System.out.print("선택: ");
    }

    public void showLoginPrompt() {
        System.out.println("\n=== 로그인 ===");
        System.out.print("아이디: ");
    }

    public void showPasswordPrompt() {
        System.out.print("비밀번호: ");
    }

    public void showLoginSuccess(String nick) {
        System.out.printf("\n%s님 환영합니다!\n", nick);
    }

    public void showRegisterPrompt() {
        System.out.println("\n=== 회원가입 ===");
        System.out.print("아이디: ");
    }

    public void showNickPrompt() {
        System.out.print("닉네임: ");
    }

    public void showRegisterSuccess() {
        System.out.println("\n회원가입이 완료되었습니다!");
    }

    public void showUserExists() {
        System.out.println("\n이미 존재하는 아이디입니다.");
    }

    public void showLoginFailed() {
        System.out.println("\n아이디 또는 비밀번호가 일치하지 않습니다.");
    }
} 