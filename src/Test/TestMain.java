
package Test;

import java.util.Scanner;

public class TestMain {
    public static void main(String[] args) {

        System.out.println("1. 직원월급 | 2. 파트타임시급");
        System.out.print("번호를 입력하세요.");
        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();

        if(choice == 1){
            // FullTime 인스턴스 생성
            FullTime employee = new FullTime("Jin", 101, "강동구", "010-123-4567", "2025-02-01", 50000, 90);

            // 성과 계산
            employee.CalPerformance();
        } else if (choice == 2) {
            // PartTime 인스턴스 생성
            PartTime partTimeEmployee = new PartTime("Min", 102, "중구", "010-987-6543", "2025-03-01",10030,5);

            // 성과 계산
            partTimeEmployee.wage();
        }

    }
}

