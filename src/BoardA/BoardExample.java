package BoardA;

import java.util.Scanner;

public class BoardExample {
    // 9. 스캐너 생성
    Scanner scanner = new Scanner(System.in);

    public void list(){
        //2.게시물 목록 출력
        System.out.println("[게시물 목록]");
        System.out.println("---------------------------------------------");
        System.out.printf("%-5s %-10s %-12s %s%n", "No", "Writer", "Date", "Title");
        System.out.println("---------------------------------------------");
        System.out.printf("%-5s %-10s %-12s %s%n", "1", "winter", "2024.01.02", "게시판에 오신 것을 환영합니다.");
        System.out.printf("%-5s %-10s %-12s %s%n", "2", "winter", "2024.01.01", "올 겨울은 많이 춥습니다.");
        mainMenu(); //4.mainMenu 메소드 호출
    };
    public void mainMenu(){
        System.out.println("---------------------------------------------");
        System.out.println("메인 메뉴: 1.Create | 2.Read | 3.Clear | 4.Exit");
        System.out.print("메뉴 선택: ");
        //9.번호 선택
        String menuNo = scanner.nextLine();
        //10. 선택한 메뉴처리
        switch (menuNo){
            case "1" :
                create();
                break;
            case "2" :
                read();
                break;
            case "3" :
                clear();
                break;
            case "4" : exit();
            default:
                System.out.println("메뉴를 잘못선택하셨습니다.");
                break;
        }
    }
    //5.create() 메소드 실행
    public void create(){
        //게시물 입력
        //Board board = new Board();
        System.out.println("[새 게시물 입력]");
        System.out.print("제목 :");
        String title = scanner.nextLine();
        System.out.print("내용 :");
        String content = scanner.nextLine();
        System.out.print("작성자 :");
        String writer = scanner.nextLine();

        //보조 메뉴 출력
        System.out.println("-----------------------");
        System.out.println("보조메뉴 : 1.ok | 2.Cancel");
        System.out.print("메뉴 선택: ");
        String menuNo = scanner.nextLine();
        list();
    }
    //6.read() 메소드 실행
    public void read(){
        System.out.println("[게시물 읽기]");
        System.out.print("bno : ");
        String menuNO = scanner.nextLine();
        //게시물 목록 출력
        list();
    }
    //7.clear() 메소드 실행
    public void clear(){
        System.out.println("clear() 메소드 실행");
        list();
    }
    //8. exit() 메소드 실행
    public void exit(){
        System.out.println("** 게시판 종료 **");
        System.exit(0);
    }

    public static void main(String[] args) {
        //1.BoardExample 객체 생성
        BoardExample boardExample = new BoardExample();
        //2.list() 메소드 호출
        boardExample.list();

    }
}
