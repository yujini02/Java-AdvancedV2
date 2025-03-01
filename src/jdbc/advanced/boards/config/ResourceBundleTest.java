package jdbc.advanced.boards.config;

import java.util.ResourceBundle;

public class ResourceBundleTest {

    public static void main(String[] args) {
        // ResourceBundle객체를 이용하여 파일 읽어오기

        // ResourceBundle객체 생성하기
        // ==> 읽어올 파일을 지정할 때 '패키지명.파일명'까지만 지정하고 확장자는 기술하지 않는다.

        ResourceBundle bundle = ResourceBundle.getBundle("jdbc.advanced.boards.config.dbinfo");

        // 읽어온 내용 출력하기
        System.out.println("driver : " + bundle.getString("driver"));
        System.out.println("url : " + bundle.getString("url"));
        System.out.println("user : " + bundle.getString("user"));
        System.out.println("pass : " + bundle.getString("password"));
    }

}