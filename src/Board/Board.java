package Board;

import lombok.Data;

import java.util.Date;
@Data
public class Board {
    private int bno;
    private String btitle;
    private String bcontent;
    private String bwriter;
    private Date date;

    public Board(int bno, String bwriter, String btitle, String bcontent){
        this.bno = bno;
        this.bwriter = bwriter;
        this.btitle = btitle;
        this.bcontent = bcontent;
        this.date = new Date();
    }
}
