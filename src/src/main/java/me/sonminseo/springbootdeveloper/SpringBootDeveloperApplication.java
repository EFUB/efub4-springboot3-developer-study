package me.sonminseo.springbootdeveloper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // 스프링 부트 사용에 필요한 기본 설정
public class SpringBootDeveloperApplication { // 자바의 메인 메서드와 같은 역할
    public static void main(String[] args){
        SpringApplication.run(SpringBootDeveloperApplication.class,args); // 애플리케이션을 실행 (인자: 메인클래스명, 커맨드라인 인수)
    }
}
