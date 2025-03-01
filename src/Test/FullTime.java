package Test;

import lombok.Data;


public class FullTime extends Employee {

    private int performance;
    private String grade;
    private int salary;

    public FullTime(String name, int empNo, String address, String phone, String hiredate, int salary, int performance) {
        super(name, empNo, address, phone, hiredate);
        this.salary = salary;
        this.performance = performance;
        this.grade = "N/A"; // 초기 등급
    }

    // 성과 등급과 그에 따른 월급 인상 적용
    public void CalPerformance() {
        if (performance >= 90) {
            grade = "A";
            applyRaise(7, grade);
        } else if (performance >= 80) {
            grade = "B";
            applyRaise(5, grade);
        } else if (performance >= 70) {
            grade = "C";
            applyRaise(3, grade);
        } else {
            grade = "D";
            applyRaise(1, grade);
        }
    }

    public void applyRaise(int salaryRate, String grade) {
        System.out.println("원래 월급은 " + salary + "입니다.");
        salary += salary * salaryRate / 100;
        System.out.println(getName() + "의 성과 등급: " + grade + " (" + salaryRate + "% 급여 인상)");
        System.out.println("새로운 월급은 " + salary + "입니다.");
    }
}