package jdbc.callablestmt;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CallableStmtEx04 {
    Connection conn = null;
    CallableStatement cs = null;


    public CallableStmtEx04() throws SQLException{
        // 1. connection 얻어오기
        conn = DBUtil.getConnction();
        conn.setAutoCommit(false);
        List<sp_memberDto> memberlist = new ArrayList<>();

        // 2. CallableStatement 객체를 이용하여 호출할 프로시저 연결
        cs = conn.prepareCall("{CALL SP_MEMBER_LIST()}"); // ?: cDATA ?: cTname ?:resultMsg
        ResultSet rs = cs.executeQuery();

        while(rs.next()){
            sp_memberDto spMemberDto = new sp_memberDto();
            spMemberDto.setM_seq(rs.getInt("m_seq"));
            spMemberDto.setM_userid(rs.getString("m_userid"));
            spMemberDto.setM_pwd(rs.getString("m_pwd"));
            spMemberDto.setM_email(rs.getString("m_email"));
            spMemberDto.setM_hp(rs.getString("m_hp"));
            spMemberDto.setM_registdate(rs.getString("m_registdate"));
            spMemberDto.setM_point(rs.getInt("m_point"));
            memberlist.add(spMemberDto);
        }
        for(sp_memberDto memberDto : memberlist) System.out.println(memberDto.toString());

        if(rs != null) rs.close();
        if(cs != null) cs.close();
        if(conn != null) conn.close();

    }

    public static void main(String[] args) throws SQLException{
        new CallableStmtEx04();
    }
}
