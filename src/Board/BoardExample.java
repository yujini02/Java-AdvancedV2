package Board;

import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class BoardExample {

    static Scanner scanner = new Scanner(System.in);
    static ArrayList<Board> boardList = new ArrayList<>();
    static int boardCount=0;
    //게시물 목록 출력
    public static void list(){
        while (true) {
            System.out.println("[게시물 목록]");
            System.out.println("-----------------------------------------------");
            System.out.println("no\twriter\tdate\ttitle");
            System.out.println("-----------------------------------------------");
            System.out.println("1\twinter\t2024.01.02\t게시판에 오신 것을 환영합니다.");
            System.out.println("2\twinter\t2024.01.01\t올 겨울은 많이 춥습니다.");
            for(Board board : boardList){
                System.out.printf("%d\t%s\t%s\t%s%n",board.getBno(),board.getBwriter(),board.getDate(),board.getBtitle());
            }
            System.out.println("-----------------------------------------------");
            System.out.println("메인메뉴: 1.Create|2.read|3.Clear|4.Exit");
            System.out.print("메뉴 선택: ");

            int menu = scanner.nextInt();

            switch (menu) {
                case 1 -> Create();
                case 2 -> Read();
                case 3 -> Clear();
                case 4 -> Exit();
                default -> System.out.println("번호를 잘못 선택하셨습니다.");
            }
        }
    }
    public static void Create(){
        System.out.println("[새 게시물 입력]");
        System.out.print("제목 :");
        String title = scanner.nextLine();
        scanner.nextLine();
        System.out.print("내용 :");
        String content = scanner.nextLine();
        System.out.print("작성자 :");
        String writer = scanner.nextLine();

        //boardList.add(new Board());

        System.out.println("-----------------------");
        System.out.println("보조 메뉴 : 1.Ok|2.Cancel");
        System.out.print("메뉴 선택: ");

        int menu = scanner.nextInt();

        if(menu == 1){
            System.out.println("게시글이 추가되었습니다.");
        }else {
            System.out.println("게시물이 삭제되었습니다.");
        }
    }
    public static void Read(){
        System.out.println("[게시물 읽기]");
        System.out.print("bno: ");
        int bno = scanner.nextInt();

        System.out.println("보조메뉴: 1.Update|2.Delete|3.List");
        System.out.print("메뉴 선택: ");
        int subMenu = scanner.nextInt();
        switch (subMenu){
            case 1 -> Update();
            case 2 -> Delete();
            case 3 -> list();
            default -> System.out.println("번호를 잘못 선택하셨습니다.");
        }
    }

    public static void Update(){
        System.out.println("update");
    }
    public static void Delete(){
        System.out.println("delete");
    }
    public static void Clear(){
        System.out.println("[게시물 전체 삭제]");
        System.out.println("---------------");
        System.out.println("보조 메뉴:1.Ok|2.Cancel");
        System.out.print("메뉴 선택: ");
        int menu = scanner.nextInt();
    }
    public static void Exit(){
        System.out.println("**게시판 종료**");
        System.exit(0);
    }
    public static void main(String[] args) {
        list();
    }
}
