package mini_project;

public class MessageView {
    public void showMessage(String message) {
        System.out.println(message);
    }

    public void showError(String message) {
        System.out.println("\n오류: " + message);
    }

    public void showInvalidChoice() {
        System.out.println("\n잘못된 선택입니다. 다시 선택해주세요.");
    }

    public void showGoodbye() {
        System.out.println("\n프로그램을 종료합니다. 안녕히 가세요!");
    }
} 