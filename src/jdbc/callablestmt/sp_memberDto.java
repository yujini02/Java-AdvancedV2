package jdbc.callablestmt;

import lombok.Data;

@Data
public class sp_memberDto {
    private int m_seq;
    private String m_userid;
    private String m_pwd;
    private String m_email;
    private String m_hp;
    private String m_registdate;
    private int m_point;

    @Override
    public String toString(){
        return m_seq + " " + m_userid + " " + m_pwd + " " + m_email + " " + m_hp + " " + m_registdate + " " + m_point;
    }
}
