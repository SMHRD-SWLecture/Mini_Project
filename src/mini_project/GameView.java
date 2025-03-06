package mini_project;

import java.util.ArrayList;

public class GameView {
    public void showGameMenu() {
        System.out.println("\n=== 음악 재생 시간 선택 ===");
        System.out.println("1. 1초(10점)");
        System.out.println("2. 3초(5점)");
        System.out.println("3. 5초(3점)");
        System.out.println("4. 10초(1점)");
        System.out.print("선택: ");
    }

    public void showAnswerPrompt() {
        System.out.print("\n답을 입력하세요: ");
    }

    public void showRanking(ArrayList<Model.User> ranking) {
        System.out.println("\n=== 랭킹 ===");
        for (int i = 0; i < ranking.size(); i++) {
            Model.User user = ranking.get(i);
            System.out.printf("%d. %s - %d점\n", i + 1, user.getNick(), user.getScore());
        }
    }

    public void showCorrect(int score) {
        System.out.println("정답입니다!");
        System.out.printf("%d점 획득!\n", score);
    }

    public void showIncorrect(int retryCount) {
        System.out.println("틀렸습니다!");
        System.out.printf("남은 기회: %d회\n", retryCount);
    }

    public void showCorrectAnswer(String answer) {
        System.out.printf("정답은 '%s'입니다.\n", answer);
    }

    public void showGameEnd(int totalScore) {
        System.out.printf("\n게임 종료! 총 획득 점수: %d점\n", totalScore);
    }

    public void showTimeout() {
        System.out.println("\n시간 초과!");
    }

    public void showNoMusicFiles() {
        System.out.println("재생 가능한 음악 파일이 없습니다.");
    }

    public void showMusicNotFound() {
        System.out.println("음악 정보를 찾을 수 없습니다.");
    }

    public void showPlaybackError(String message) {
        System.out.println("음악 재생 중 오류가 발생했습니다: " + message);
    }

    public void showUnsupportedFormat() {
        System.out.println("지원하지 않는 음악 형식입니다.");
    }
} 