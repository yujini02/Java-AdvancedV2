package Board;

import java.util.Scanner;

public class BoardExample {

    static Scanner scanner = new Scanner(System.in);
    //게시물 목록 출력
    public static void list(){
        while (true) {
            System.out.println("[게시물 목록]");
            System.out.println("-----------------------------------------------");
            System.out.println("no\twriter\tdate\ttitle");
            System.out.println("-----------------------------------------------");
            System.out.println("1\twinter\t2024.01.02\t게시판에 오신 것을 환영합니다.");
            System.out.println("2\twinter\t2024.01.01\t올 겨울은 많이 춥습니다.");
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
        System.out.println("--------------");
        System.out.println("보조 메뉴 : 1.Ok|2.Cancel");
        System.out.print("메뉴 선택: ");

    }
    public static void Read(){

    }
    public static void Clear(){

    }
    public static void Exit(){

    }
    public static void main(String[] args) {
        list();
    }
}
