package Board;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class BoardExample {
    Scanner scanner = new Scanner(System.in);
    private final Map<Integer,Board> boardMap = new HashMap<>();
    private final Map<Integer,Runnable> mainMenu = new HashMap<>(); //값으로 함수형 인터페이스 runnable을 맵의 값으로 지정

    public void play(){
        while (true) {
            list();
            mainMenu();
            int menuchoice = scanner.nextInt();
            scanner.nextLine();
            Runnable action = mainMenu.get(menuchoice); //입력한 번호를 키로 메인메뉴의 값을 가져와 action에 저장
            if (action != null) { //값이 있으면 해당 runnable의 run메서드 실행
                action.run();
            } else System.out.println("다시 선택해주세요.");
        }
    }
    public void list(){
        System.out.println("[게시물 목록]");
        System.out.println("-".repeat(50));
        System.out.println("no\twrite\tdate\ttitle");
        System.out.println("-".repeat(50));
        boardMap.forEach((integer, board) ->
                System.out.println(board.getBno()+"\t" +
                        board.getBwriter() + "\t" +
                        board.getNowDate() + "\t"+
                        board.getBtitle()));
    }
    public void mainMenu(){
        System.out.println("-".repeat(50));
        System.out.println("메인메뉴 : 1.Create | 2.Read | 3.Clear | 4.Exit");
        System.out.print("메뉴선택 : ");
        mainMenu.put(1,this::create); // 키값 1의 값에 runnable의 run메서드에 create메서드를 호출
        mainMenu.put(2,this::read);
        mainMenu.put(3,this::clear);
        mainMenu.put(4,this::exit);

    }

    public int submenu() {
        int submenuchoice = 0;
        boolean check = false;
        while (!check) {
            System.out.println("보조 메뉴 : 1.ok | 2.Cancel");
            System.out.print("보조 메뉴 선택 : ");
            submenuchoice = scanner.nextInt();
            scanner.nextLine();
            switch (submenuchoice){
                case 1 :
                    check = true;
                    break;
                case 2 :
                    System.out.println("취소합니다.");
                    check = true;
                    break;
                case 3:
                    System.out.println("다시 선택해주세요.");
            }
        }
        return submenuchoice;
    }

    public Map<Integer,Board> create(){
        System.out.println("[새 게시물 입력]");
        System.out.print("제목 : ");
        String bTitle = scanner.nextLine();
        System.out.print("내용 : ");
        String bContent = scanner.nextLine();
        System.out.print("작성자 : ");
        String bWriter =scanner.nextLine();
        System.out.println("-".repeat(50));
        if(submenu() == 1){
            Board board = new Board(bTitle,bContent,bWriter);
            boardMap.put(board.getBno(),board);
        }
        return boardMap;
    }

    public Map<Integer,Board> read(){
        System.out.println("[게시물 읽기]");
        int readBno = findboard();
        System.out.println(boardMap.get(readBno).toString());
        System.out.println("-".repeat(50));
        while (true) {
            System.out.println("보조 메뉴 : 1.Update | 2.Delete | 3.List");
            System.out.print("메뉴선택 : ");
            int submenuchoice = scanner.nextInt();
            scanner.nextLine();
            if (submenuchoice == 1) {
                update(readBno);
                break;
            } else if (submenuchoice == 2){
                delete(readBno);
                break;
            } else if(submenuchoice ==3){
                System.out.println("게시글 목록으로 돌아갑니다.");
                break;
            }
            else System.out.println("잘못누르셨습니다.");
        }
        return boardMap;
    }

    public Map<Integer,Board> update(int readbno){
        System.out.println("[수정 내용 입력]");
        System.out.print("제목 : ");
        String bTitle = scanner.nextLine();
        System.out.print("내용 : ");
        String bContent = scanner.nextLine();
        System.out.print("작성자 : ");
        String bWriter =scanner.nextLine();
        if(submenu() == 1){
            Board boardkey = boardMap.get(readbno);
            boardkey.setBtitle(bTitle);
            boardkey.setBcontent(bContent);
            boardkey.setBwriter(bWriter);
        }
        return boardMap;
    }
    public Map<Integer, Board> delete(int readbno) {
        if (boardMap.isEmpty()) {
            System.out.println("등록된 게시글이 없습니다.");
        } else {
            boardMap.remove(readbno);
            for (int bno = readbno + 1; bno <= Board.getBnoCnt(); bno++) {
                Board deleteBoard = boardMap.get(bno);
                if (deleteBoard != null) {
                    deleteBoard.setBno(bno - 1);
                    boardMap.put(bno - 1, deleteBoard);
                    boardMap.remove(bno);
                }
            }
            Board.setBnoCnt(boardMap.size());
        }
        return boardMap;
    }


    public int findboard() {
        int readBno;
        while (true) {
            System.out.print("bno: ");
            readBno = scanner.nextInt();
            scanner.nextLine();
            if (boardMap.get(readBno) == null) {
                System.out.println("해당 게시물이 존재하지 않습니다.");
                System.out.println("다시 입력해주세요.");
            } else break;
        }
        return readBno;
    }
    public Map<Integer,Board> clear(){
        System.out.println("[게시물 전체 삭제]");
        System.out.println("-".repeat(50));
        if(submenu()==1)
            boardMap.clear();
            Board.setBnoCnt(1);
        return boardMap;
    }

    public void exit(){
        System.exit(0);
    }
}
