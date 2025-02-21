package BoardA;

import java.util.ArrayList;
import java.util.Scanner;

public class BoardAExample {

    static Scanner scanner = new Scanner(System.in);
    static ArrayList<BoardA> boardList = new ArrayList<>();
    static int boardCount=0;
    //게시물 목록 출력
    public static void list(){
        while (true) {
            System.out.println("[게시물 목록]");
            System.out.println("-----------------------------------------------");
            System.out.println("no\twriter\tdate\ttitle");
            System.out.println("-----------------------------------------------");
//            System.out.println("1\twinter\t2024.01.02\t게시판에 오신 것을 환영합니다.");
//            System.out.println("2\twinter\t2024.01.01\t올 겨울은 많이 춥습니다.");
            for(BoardA board : boardList){
                System.out.printf("%d\t%s\t%s\t%s%n",board.getBno(),board.getBwriter(),board.getDate(),board.getBtitle());
            }
            System.out.println("-----------------------------------------------");
            System.out.println("메인메뉴: 1.Create|2.read|3.Clear|4.Exit");
            System.out.print("메뉴 선택: ");

            int menu = scanner.nextInt();
            scanner.nextLine();

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
        System.out.print("내용 :");
        String content = scanner.nextLine();
        System.out.print("작성자 :");
        String writer = scanner.nextLine();

        boardList.add(new BoardA(boardCount++,writer,title,content));

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

        /////////////////////////////////
        System.out.println("#######");

            /**
             * 학번 이름 성적
             * 1 박건희 100
             * 2 홍길동 100
             * 3 김김김 100
             * 4 이이이 100
             * 5 박박박 100
             * -> studentList
             *
             * 조회할 학생 학번 : bno
             *
             * 학번 : 2 studentList에서 ((학번이 bno인 학생))의 학번)
             * 이름 : 홍길동 studentList에서 ((학번이 bno인 학생)의 이름)
             * 성적 : 100 studentList에서 ((학번이 bno인 학생)의 성적)
             *
             * studentList.get(bno).getBno()
             * studentList.get(bno).getBName()
             * studentList.get(bno).getScore()
             */

        System.out.print("번호 :");
        System.out.println(bno);
        System.out.print("제목 :");
        System.out.println(boardList.get(bno).getBtitle());
        //boardList : 게시판
        //boardList.get(bno) : 게시물 번호가 bno인 게시물을 가져온다
        //boardList.get(bno).getBtitle() : 게시물 번호가 bno인 게시물의 제목을 가져온다
        System.out.print("내용 :");
        System.out.println(boardList.get(bno).getBcontent());
        System.out.print("작성자 :");
        System.out.println(boardList.get(bno).getBwriter());
        System.out.print("날짜 :");
        System.out.println(boardList.get(bno).getDate());

        System.out.println("#######");


        ////////////////////////////////

        System.out.println("보조메뉴: 1.Update|2.Delete|3.List");
        System.out.print("메뉴 선택: ");
        int subMenu = scanner.nextInt();
        scanner.nextLine();

        /**
         *
         * 숫자 입력 : \n
         *
         */


        switch (subMenu){
            case 1 -> Update(bno);
            case 2 -> Delete(bno);
            case 3 -> list();
            default -> System.out.println("번호를 잘못 선택하셨습니다.");
        }
    }

    public static void Update(int bno){
        //번호가 bno인 게시물의 제목,내용,작성자를 수정
        System.out.println("[수정 내용 입력]");
        System.out.print("제목 :");
        String ntitle = scanner.nextLine();
        System.out.print("내용 :");
        String ncontent = scanner.nextLine();
        System.out.print("작성자 :");
        String nwriter = scanner.nextLine();

        System.out.println("---------------");
        System.out.println("보조 메뉴: 1.Ok|2.Cancel");
        System.out.print("메뉴 선택: ");
        int menu = scanner.nextInt();
        if(menu == 1){
            System.out.println("ok선택");
            boardList.get(bno).setBtitle(ntitle);
            //boardList의 bno 게시물의 제목을 넣어준다. nititle을 넣어준다.
            boardList.get(bno).setBcontent(ncontent);
            boardList.get(bno).setBwriter(nwriter);
        }

    }
    public static void Delete(int bno){
        boardList.remove(bno);
        System.out.println("delete");
    }
    public static void Clear(){
        System.out.println("[게시물 전체 삭제]");
        System.out.println("---------------");
        System.out.println("보조 메뉴:1.Ok|2.Cancel");
        System.out.print("메뉴 선택: ");
        int menu = scanner.nextInt();
        if(menu == 1){
            boardList.clear();
        }
    }
    public static void Exit(){
        System.out.println("**게시판 종료**");
        System.exit(0);
    }
    public static void main(String[] args) {
        list();
    }
}
