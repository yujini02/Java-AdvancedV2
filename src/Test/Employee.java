package Test;

import lombok.Data;

@Data
public abstract class Employee{
    private String name;
    private int empNo;
    private String address;
    private String phone;
    private String hiredate;

    public Employee(String name, int empNo, String address, String phone, String hiredate) {
        this.name = name;
        this.empNo = empNo;
        this.address = address;
        this.phone = phone;
        this.hiredate = hiredate;
    }
}
