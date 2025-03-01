package jdbc.advanced.users;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

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
     * @return null
     */
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/ssgdb?serverTimezone=Asia/Seoul",
                    "ssg",
                    "ssg1234"
            );

        } catch (SQLException e) {
            System.out.println("연결 실패!!!");
            return null;
        }
    }
}