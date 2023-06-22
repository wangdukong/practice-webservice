package com.practice.springboot.config.auth;

import com.practice.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity // Security 설정들을 활성화 시켜줌
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
                .csrf().disable()
                .headers().frameOptions().disable()
                //h2-console 화면을 사용하기 위해 해당 옵션을 disable
                .and()
                .authorizeRequests()
                //url 별 권한 관리를 설정하는 옵션의 시작점

                //antMatcher 권한 관리 대상을 지정하는 옵션, url, http 메소드 별로 관리
                // "/" 등으로 지정된 url들은 permitAll() 옵션을 통해 전체 열람권한

                //"api/v1/ 하위 url는 USER권한을 가진사람만 열람 가능
                .antMatchers("/","/css/**","/images/**","/js/**","/h2-console/**").permitAll()
                .antMatchers("/api/v1/**").hasRole(Role.USER.name())
                .anyRequest().authenticated()
                //anyRequest 설정된 값들 이회 나머지 url들을 나타낸다.
                // 여기서는 authenticated()를 추가하여 나머지 url들은 모두 인증된 사용자들에게만 허용
                // 인증된 사용자 즉, 로그인한 사용자들
                .and()
                .logout().logoutSuccessUrl("/")
                //로그아웃 기능에 대한 여러 설정의 진입점
                // 로그아웃 성공시 "/" 주소로 이동
                .and()
                .oauth2Login().userInfoEndpoint()// Oauth2 로그인 성공 이후 사용자 정보를 가져올때 설정 담당.
                .userService(customOAuth2UserService); // 소셜 로그인 성공시 후속 조치를 진행할 UserService 인터페이싀 구현체를 등록

    }
}
