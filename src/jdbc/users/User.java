package jdbc.users;

import lombok.Data;

/**
 * packageName   : jdbc
 * fileName      : ConnectionEx
 * author        : sym
 * date          : 2025-02-27
 * description   :
 * =================================================
 * DATE             AUTHOR             NOTE
 * -------------------------------------------------
 * 2025-02-27       j               한 사용자의 정보를 담는 객체
 */
@Data
public class User {
    private String userid;
    private String username;
    private String userpassword;
    private int age;
    private String email;

}
