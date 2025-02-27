package jdbc.users;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserInsert {
    public static void main(String[] args) {
        Connection connection = null;

        try {
            // 1. JDBC 드라이버 등록 : MYSQL DB 접근 하기 위한 드라이버 등록
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loaded ok!" + connection);

            // 2. Mysql DB에 연결객체 얻어와서 연결하기
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ssgdb?serverTimezone=Asia/Seoul","ssg","ssg1234");
            System.out.println("Connection OK" + connection);

            //3. 매개변수화된 SQL 문 작성

            String query = "" +
                           "INSERT INTO users (userid,username,userpassword, userage, useremail)" +
                             "VALUES (?,?,?,?,?);";

            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1,"jin02");
            pstmt.setString(2,"정유진");
            pstmt.setString(3,"1234");
            pstmt.setInt(4,30);
            pstmt.setString(5,"jin@gmail.com");

            //4.SQL문 실행
            int rows = pstmt.executeUpdate();
            System.out.println(rows + "rows inserted");
            // 5. PreparedStaatement 객체 닫기
            pstmt.close();

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if(connection != null){
                try {
                    connection.close();
                    System.out.println("connection closed");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
