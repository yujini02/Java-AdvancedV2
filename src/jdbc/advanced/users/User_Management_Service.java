package jdbc.advanced.users;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
/**
 * packageName   : jdbc.advanced.users
 * fileName      : User_Management_Service
 * author        : j
 * date          : 2025-02-28
 * description   :
 *
 * 	회원을 관리하는 프로그램을 작성하시오.   => 자신 코드 스타일로 stream, lamda 사용하셔도 됩니다.
 * 	(어제 만든 ssgdb.users 테이블 이용)
 *
 * 	아래 메뉴의 기능을 모두 구현하시오. (CRUD기능 구현하기)
 * 	메뉴 예시)
 * 		== 작업 선택 ==
 * 		1. 자료 추가
 * 		2. 자료 삭제
 * 		3. 자료 수정
 * 		4. 전체 자료 출력
 * 		0. 작업 끝.
 *
 * 	처리조건)
 * 	1) 자료 추가에서 '회원ID'는 중복되지 않는다.(중복되면 다시 입력 받는다.)
 * 	2) 삭제는 '회원ID'를 입력 받아서 처리한다.
 * 	3) 자료 수정에서 '회원ID'는 변경되지 않는다.
 *
 *
 * */

public class User_Management_Service {

    private Scanner scan = new Scanner(System.in);

    public static void main(String[] args) { //1
        //시작
        new User_Management_Service().memberStart();
    }

    public void memberStart(){
        // 메뉴 선택
        while(true){ //무한 반복
            int choice = displayMenu(); // 입력 숫자를 가져와서 choice에 넣음 2
            switch(choice){   //choice에 따라 번호를 선택 3
                case 1 :	// 추가
                    insertMember(); break; //insertMember 함수로 들어감 4
                case 2 : 	// 삭제
                    deleteMember(); break;
                case 3 : 	// 수정  ==> 전체 항목 수정
                    updateMember(); break;
                case 4 : 	// 전체 자료 출력
                    displayMember(); break;
                case 5 : 	// 수정	==> 원하는 항목만 수정
                    updateMember2(); break;
                case 0 : 	// 종료
                    System.out.println("작업을 마칩니다.");
                    return;
                default :
                    System.out.println("번호를 잘못 입력했습니다. 다시입력하세요.");
            }
        }
    }

    // 회원 정보를 추가하는 메서드
    private void insertMember(){ //4
        Connection conn = null; //db연결을 위한 connection(도로)를 선언함
        PreparedStatement pstmt = null; //db연결을 위한 PreparedStatement(자동차)를 선언함

        System.out.println();
        System.out.println("추가할 회원 정보를 입력하세요.");

        int count = 0;
        String memId = null;  // 회원ID가 저장될 변수
        do{
            System.out.print("회원ID >> ");
            memId = scan.next(); //내가 추가하고 싶은 회원ID
            count = getMemberCount(memId); //5
            if(count>0){
                System.out.println(memId + "은(는) 이미 등록된 회원ID입니다.");
                System.out.println("다른 회원ID를 입력하세요.");
            }

        }while(count>0);

        System.out.print("회원이름 >> ");
        String memName = scan.next();

        System.out.print("비밀번호 >> ");
        String memPass = scan.next();

        System.out.print("전화번호 >> ");
        String memTel = scan.next();

        scan.nextLine();  // 입력 버퍼 비우기
        System.out.print("회원주소 >> ");
        String memAddr = scan.nextLine();

        try {
            //DB 연결  후 쿼리 작성
            conn = DBUtil.getConnection();

            String sql = "INSERT INTO ssgdb.mymember (mem_id,mem_name, mem_pass, mem_tel, mem_address) VALUES (?, ?, ?, ?,?)";
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, memId);
            pstmt.setString(2, memName);
            pstmt.setString(3, memPass);
            pstmt.setString(4, memTel);
            pstmt.setString(5, memAddr);

            int cnt = pstmt.executeUpdate();

            if( cnt>0 ){
                System.out.println("회원 정보 추가 성공!!!");
            }else{
                System.out.println("회원 정보 추가 실패~~~");
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(pstmt!=null) try{ pstmt.close();   }catch(SQLException e){}
            if(conn!=null) try{ conn.close();   }catch(SQLException e){}
        }

    }

    // 회원 정보를 삭제하는 메서드
    private void deleteMember(){
        Connection conn = null; // 데이터베이스 연결을 나타내는 Connection 객체 선언
        PreparedStatement pstmt = null; //// SQL 쿼리를 실행하기 위한 PreparedStatement 객체 선언

        System.out.println();
        System.out.println("삭제할 회원 정보를 입력하세요.");
        System.out.print("삭제할 회원ID >> ");
        String memId = scan.next();

        try {
            conn = DBUtil.getConnection();

            String sql = "DELETE FROM ssgdb.mymember WHERE mem_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, memId );

            int cnt = pstmt.executeUpdate();

            if(cnt>0){
                System.out.println("회원ID가 " + memId + "인 회원 삭제 성공!!");
            }else{
                System.out.println(memId + "은(는) 없는 회원ID이거나 "
                        + "삭제에 실패했습니다.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(pstmt!=null) try{ pstmt.close();   }catch(SQLException e){}
            if(conn!=null) try{ conn.close();   }catch(SQLException e){}
        }
    }

    // 회원 정보를 수정하는 메서드  ==> 전체 항목 수정
    private void updateMember(){
        Connection conn = null;
        PreparedStatement pstmt = null;

        System.out.println();
        System.out.println("수정할 회원 정보를 입력하세요.");
        System.out.print("수정할 회원ID >> ");
        String memId = scan.next();

        int count = getMemberCount(memId);
        if(count==0){ // 없는 회원이면...
            System.out.println(memId + "은(는) 없는 회원ID입니다.");
            System.out.println("수정 작업을 중단합니다.");
            return;
        }

        System.out.println();
        System.out.println("수정할 내용을 입력하세요.");
        System.out.print("새로운 회원이름 >> ");
        String newMemName = scan.next();

        System.out.print("새로운 비밀번호 >> ");
        String newMemPass = scan.next();

        System.out.print("새로운 전화번호 >> ");
        String newMemTel = scan.next();

        scan.nextLine();
        System.out.print("새로운 회원주소 >> ");
        String newMemAddr = scan.nextLine();

        try {
            conn = DBUtil.getConnection();

            String sql = "UPDATE ssgdb.mymember SET mem_name = ?, mem_pass = ?, mem_tel = ?,mem_address = ? WHERE mem_id = ?";
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, newMemName);
            pstmt.setString(2, newMemPass);
            pstmt.setString(3, newMemTel);
            pstmt.setString(4, newMemAddr);
            pstmt.setString(5, memId);

            int cnt = pstmt.executeUpdate();

            if(cnt>0){
                System.out.println(memId + "회원 정보 수정 완료!!!");
            }else{
                System.out.println(memId + "회원 정보 수정 실패~~~");
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            if(pstmt!=null) try{ pstmt.close();   }catch(SQLException e){}
            if(conn!=null) try{ conn.close();   }catch(SQLException e){}
        }

    }

    // 회원 정보를 수정하는 메서드  ==> 원하는 항목 수정
    private void updateMember2(){
        Connection conn = null;
        PreparedStatement pstmt = null;

        System.out.println();
        System.out.println("수정할 회원 정보를 입력하세요.");
        System.out.print("수정할 회원ID >> ");
        String memId = scan.next();

        int count = getMemberCount(memId);
        if(count==0){ // 없는 회원이면...
            System.out.println(memId + "은(는) 없는 회원ID입니다.");
            System.out.println("수정 작업을 중단합니다.");
            return;
        }

        int num; // 수정할 컬럼에 대한 선택 값이 저장될 변수
        String updateField = null;
        String updateTitle = null;
        do{
            System.out.println();
            System.out.println("수정할 항목을 선택하세요.");
            System.out.println(" 1.회원이름  2.비밀번호  3.전화번호  4.회원주소");
            System.out.println("----------------------------------------------");
            System.out.print("수정할 항목 선택 >> ");
            num = scan.nextInt();

            switch(num){
                case 1 : updateField = "mem_name";
                    updateTitle = "회원이름"; break;
                case 2 : updateField = "mem_pass";
                    updateTitle = "비밀번호"; break;
                case 3 : updateField = "mem_tel";
                    updateTitle = "전화번호"; break;
                case 4 : updateField = "mem_addr";
                    updateTitle = "회원주소"; break;
                default :
                    System.out.println("수정할 항목을 잘못 선택했습니다.");
                    System.out.println("다시 선택하세요.");
            }
        }while(num<1 || num>4);

        scan.nextLine();  // 입력 버퍼 비우기
        System.out.println();
        System.out.print("새로운 " + updateTitle + " >> ");
        String updateData = scan.nextLine();

        try {
            conn = DBUtil.getConnection();

            String sql = "UPDATE mymember SET " + updateField + " = ? WHERE mem_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, updateData);
            pstmt.setString(2, memId);

            int cnt = pstmt.executeUpdate();

            if(cnt>0){
                System.out.println(memId + " 회원 정보 수정 완료!!!");
            }else{
                System.out.println(memId + " 회원 정보 수정 실패~~~");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(pstmt!=null) try{ pstmt.close();   }catch(SQLException e){}
            if(conn!=null) try{ conn.close();   }catch(SQLException e){}
        }

    }


    // 전체 회원 정보를 출력하는 메서드
    private void displayMember(){
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        System.out.println();
        System.out.println("===============================================");
        System.out.println(" 회원ID   회원이름  비밀번호   전화번호    주소");
        System.out.println("===============================================");

        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT mem_id, mem_name, mem_pass, mem_tel, mem_address FROM mymember";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String memId = rs.getString("mem_id");
                String memName = rs.getString("mem_name");
                String memPass = rs.getString("mem_pass");
                String memTel = rs.getString("mem_tel");
                String memAddr = rs.getString("mem_address");

                System.out.printf("%-10s %-10s %-10s %-15s %-15s\n", memId, memName, memPass, memTel, memAddr);
            }

            System.out.println("===============================================");
            System.out.println("출력 작업 끝...");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(rs!=null) try{ rs.close();   }catch(SQLException e){}
            if(stmt!=null) try{ stmt.close();   }catch(SQLException e){}
            if(conn!=null) try{ conn.close();   }catch(SQLException e){}
        }
            //return userlist; ?
    }

    // 회원ID를 인수값으로 받아서 해당 회원ID의 개수를 반환하는 메서드
    private int getMemberCount(String memId){ //ID 중복체크 memId와 동일한 ID를 중복체크함 //5
        Connection conn = null; //도로(선언)
        PreparedStatement pstmt = null; //자동차
        ResultSet rs = null; // 결과 저장 (SQL 쿼리의 결과를 저장할 ResultSet 객체 선언)

        int count = 0; // memId의 개수를 저장할 변수 초기화
        try {
            conn = DBUtil.getConnection(); //도로 생성(초기화/행동으로 옮김)
            String sql = "SELECT COUNT(*) AS cnt FROM mymember WHERE mem_id = ?";
            /*String sql = new StringBuilder()
                    .append("SELECT mem_id FROM MYMEMBER;").toString;*/
            pstmt = conn.prepareStatement(sql); //자동차 생성 , 껍데기
            pstmt.setString(1, memId); // 채우고

            rs = pstmt.executeQuery(); //주행, 시동, SQL 쿼리를 실행하고 결과를 ResultSet 객체에 저장

//            if (rs.next()) {
//                count = rs.getInt("cnt");
//            }
            while (rs.next()) {
                if(rs.getString("memId").equals(memId)) count++;
            }

            //System.out.println(count);

        } catch (SQLException e) {
            count = 0;
            e.printStackTrace();
        } finally {
            if(rs!=null) try{ rs.close();   }catch(SQLException e){}
            if(pstmt!=null) try{ pstmt.close();   }catch(SQLException e){}
            if(conn!=null) try{ conn.close();   }catch(SQLException e){}
        }

        return count;

    }

    // 메뉴를 출력하고 선택한 작업 번호를 반환하는 메서드
    private int displayMenu(){ //2
        //메뉴 보여주는 함수
        System.out.println();
        System.out.println("== 작업 선택 ==");
        System.out.println("1. 자료 추가 ");
        System.out.println("2. 자료 삭제");
        System.out.println("3. 자료 수정");
        System.out.println("4. 전체 자료 출력");
        System.out.println("5. 자료 수정2");
        System.out.println("0. 작업 끝.");
        System.out.println("==================");
        System.out.print("원하는 작업 선택 >> ");
        //번호 입력
        int num = scan.nextInt();
        //입력 번호 반환
        return num;
    }


}