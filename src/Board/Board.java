package Board;

import lombok.Data;
import lombok.Getter;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class Board {
    private int bno;
    @Getter
    private static int bnoCnt = 1;
    private  String btitle;
    private  String bcontent;
    private  String bwriter;
    private Date bdate ;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
    String nowDate;

    public static void setBnoCnt(int bnoCnt) {
        Board.bnoCnt = bnoCnt;
    }

    public Board(){}
    public Board(String btitle, String bcontent, String bwriter) {
        this.btitle = btitle;
        this.bcontent = bcontent;
        this.bwriter = bwriter;
        this.bno =  bnoCnt++;
        this.bdate = new Date();
        this.nowDate = dateFormat.format(bdate);
    }


    @Override
    public String toString() {
        return  "#".repeat(15)+
                "\n번호: " + bno +
                "\n제목: " + btitle +
                "\n내용: " + bcontent +
                "\n작성자: " + bwriter +
                "\n날짜: " + nowDate + "\n" +
                "#".repeat(15);
    }
}

