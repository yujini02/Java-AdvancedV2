package jdbc.homework.boards;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class BoardService {
    private final BoardDao boardDao = new BoardDao();

    public void createBoard(String title, String content, String writer) {
        Board board = new Board();
        board.setBtitle(title);
        board.setBcontent(content);
        board.setBwriter(writer);
        board.setBdate(getCurrentDate());

        boardDao.insertBoard(board);
    }

    public void listBoards() {
        System.out.println("[게시물 목록]");
        System.out.println("-------------------------------------------------");
        System.out.printf("%-10s %-15s %-20s %-40s\n", "no", "writer", "date", "title");
        System.out.println("-------------------------------------------------");

        List<Board> boards = boardDao.getAllBoards();
        for (Board board : boards) {
            System.out.printf("%-10s %-15s %-20s %-40s\n",
                    board.getBno(), board.getBwriter(), board.getBdate(), board.getBtitle());
        }
    }

    public void readBoard(int bno) {
        Board board = boardDao.getBoardById(bno);
        if (board == null) {
            System.out.println("게시물을 찾을 수 없습니다.");
            return;
        }

        System.out.println("##########");
        System.out.println("번호: " + board.getBno());
        System.out.println("제목: " + board.getBtitle());
        System.out.println("내용: " + board.getBcontent());
        System.out.println("작성자: " + board.getBwriter());
        System.out.println("날짜: " + board.getBdate());
        System.out.println("##########");

        System.out.println("---------------------------");
        System.out.println("보조 메뉴: 1. Update | 2. Delete | 3. List");
        System.out.print("메뉴 선택: ");

        Scanner sc = new Scanner(System.in);
        String menuNo = sc.nextLine();

        switch (menuNo) {
            case "1" -> updateBoard(bno);
            case "2" -> deleteBoard(bno);
            case "3" -> listBoards();
        }
    }

    public void updateBoard(int bno) {
        Scanner sc = new Scanner(System.in);
        System.out.println("[수정 내용 입력]");
        System.out.print("제목: ");
        String newTitle = sc.nextLine();
        System.out.print("내용: ");
        String newContent = sc.nextLine();
        System.out.print("작성자: ");
        String newWriter = sc.nextLine();

        boardDao.updateBoard(bno, newTitle, newContent, newWriter);
        listBoards();
    }

    public void deleteBoard(int bno) {
        boardDao.deleteBoard(bno);
        listBoards();
    }

    public void clearBoards() {
        boardDao.clearAllBoards();
    }

    private String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(new Date());
    }
}
