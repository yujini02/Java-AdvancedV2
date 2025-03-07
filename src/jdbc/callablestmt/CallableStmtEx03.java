package jdbc.callablestmt;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

public class CallableStmtEx03 {
    Connection conn = null;
    CallableStatement cs = null;

    public CallableStmtEx03() throws SQLException{
        // 1. connection 얻어오기
        conn = DBUtil.getConnction();
        conn.setAutoCommit(false);
        /*// 2. CallableStatement 객체를 이용하여 호출할 프로미터 연결
        cs = conn.prepareCall("{call SP_MEMBER_INSERT(?,?,?,?,?,?)}"); // ?: cDATA ?: cTname ?:resultMsg
        // 3. 바인드 변수에 값을 세팅 inprarmenter(?) 에 값 넣기

        cs.setString(1,"TB_MEMBER"); //TABLE
        cs.setString(2,"jin"); //ID
        cs.setString(3,"1234"); //PASSWORD
        cs.setString(4,"jin@gmail.com"); //EMAIL
        cs.setString(5,"010-123-456"); //HP

        //4. out 파라미터 에 저장된 프로시저의 수행결과에 대한 외부 변수 등록
        cs.registerOutParameter(6, Types.VARCHAR);

        // 5. 쿼리수행
        // flag의 값은 resultSet 객체의 경우(select)는 true, 갱신카운트 또는 결과가 없는 false(insert,update) 리턴 됨
        boolean flag = cs.execute();
        System.out.println(flag);
        String resultMsg = cs.getString(5);
        System.out.println(resultMsg);*/


        // 2. CallableStatement 객체를 이용하여 호출할 프로시저 연결
        cs = conn.prepareCall("{call SP_MEMBER_INSERT(?,?,?,?,?)}"); // ?: cDATA ?: cTname ?:resultMsg
        // 3. 바인드 변수에 값을 세팅 inprarmenter(?) 에 값 넣기

        cs.setString(1,"black"); //TABLE
        cs.setString(2,"1234"); //ID
        cs.setString(3,"black@gmail.com"); //PASSWORD
        cs.setString(4,"010-112-233"); //EMAIL

        //4. out 파라미터 에 저장된 프로시저의 수행결과에 대한 외부 변수 등록
        cs.registerOutParameter(5, Types.VARCHAR);

        // 5. 쿼리 수행
        // flag 의 값은 resultSet 객체의 경우는 true, 갱신카운트 또는 결과가 없는 flase 리턴 됨
        cs.execute();
        int rtn = cs.getInt(5);
        System.out.println(rtn);
        String resultString = null;
        if(rtn == 100) {
            resultString = "이미 가입된 회원입니다.";
            conn.rollback();
        } else {
            resultString = "회원 가입 성공";
        }
        System.out.println();

        if(cs != null) cs.close();
        if(conn != null) conn.close();

    }

    public static void main(String[] args) throws SQLException{
        new CallableStmtEx03();
    }
}
