package com.practice.springboot.web;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)// JUnit 에 내장된 실행자 외에 다른 실행자를 실행시킴
// 여기서는 SpringRunner.class라는 실행자를 사용
@WebMvcTest(controllers = HelloController.class) //Web에 집중할 수 있는 어노테이션
// @Controller, @ControllerAdvice를 사용할 수 있다.

public class HelloControllerTest {
    @Autowired // 스프링이 관리하는 Bean을 주입받음.
    private MockMvc mvc;
    //웹 API를 테스트할 때 사용
    // 스프링 MVC테스트의 시작점
    // HTTP GET,POST 등에 대한 API 테스트 가능

    @Test
    public void hello가_리턴된다() throws Exception{
        String hello="hello";

        mvc.perform(get("/hello")) //mockmvc를 통해 /hello 주소로 GET요청을 보냄.체이닝 지원
                .andExpect(status().isOk())// mvc.perform의 결과를 검증, 여기선 OK즉 200인지 아닌지를 검증
                .andExpect(content().string(hello)); //mvc.perform의 결과를 검증, 응답 본문의 내용을 검증

    }

}
