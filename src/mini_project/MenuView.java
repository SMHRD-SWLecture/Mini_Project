package mini_project;

public class MenuView {
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

    public static void main(String[] args) {
        Controller controller = new Controller();
        controller.start();
    }
} 