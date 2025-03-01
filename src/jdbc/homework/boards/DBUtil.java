package jdbc.homework.boards;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * packageName   : jdbc.advanced.users
 * fileName      : DBUtil
 * author        : a
 * date          : 2025-02-28
 * description   : 데이터베이스 연결 작업 클래스
 * =================================================
 * DATE             AUTHOR             NOTE
 * -------------------------------------------------
 * 2025-02-28        j
 */
public class DBUtil {

    /**
     * Gets connection
     * @return
     */
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/testdb?serverTimezone=Asia/Seoul",
                    "testdb",
                    "1234"
            );

        } catch (SQLException e) {
            System.out.println("연결 실패!!!");
            return null;
        }
    }
}