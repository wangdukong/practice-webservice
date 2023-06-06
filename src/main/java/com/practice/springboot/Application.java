package com.practice.springboot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication // 스프링 부트의 자동 설정, 스프링 bean 읽기와 생성을 모두 자동으로 해줌.
// 이 이노테이션이 있는 위치부터 설정을 읽어가기에 항상 프로젝트의 최상단에 위치.
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class,args); // 내장 WAS(web application server) 실행
    }
}
