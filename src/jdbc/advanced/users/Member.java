package jdbc.advanced.users;

import lombok.Data;

@Data
public class Member {
    private int mem_id;
    private String mem_name;
    private String tel;
    private String address;
}
