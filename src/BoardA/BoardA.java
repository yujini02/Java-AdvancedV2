package BoardA;

import lombok.Data;

import java.util.Date;
@Data
public class BoardA {
    private int bno;
    private String btitle;
    private String bcontent;
    private String bwriter;
    private Date date;

    public BoardA(int bno, String bwriter, String btitle, String bcontent){
        this.bno = bno;
        this.bwriter = bwriter;
        this.btitle = btitle;
        this.bcontent = bcontent;
        this.date = new Date();
    }
}
