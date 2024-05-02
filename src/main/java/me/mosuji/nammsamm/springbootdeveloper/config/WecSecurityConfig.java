package me.mosuji.nammsamm.springbootdeveloper.config;

import lombok.RequiredArgsConstructor;
import me.mosuji.nammsamm.springbootdeveloper.service.UserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@Configuration
@RequiredArgsConstructor
public class WecSecurityConfig {

    private final UserDetailService userService;

    // 스프링 시큐리티 기능 비활성화 :   인증, 인가 서비스를 모든 곳에 적용하지는 않음. 일반적으로 리소스 파일에 설정함.
    @Bean
    public WebSecurityCustomizer configure(){
        return (web -> web.ignoring()
                .requestMatchers(toH2Console())
                .requestMatchers("/static/**"));
    }

    // 특정 HTTP 요청에 대한 웹 기반 보안 구성
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        return http
                .authorizeHttpRequests() // 인증, 인가 설정

                // requestMatchers() : 특정 요청과 일치하는 url에 대한 액세스를 설정함.
                // permitAll() : 누구나 접근이 가능하게 설정함. 즉 해당 경로로 요청이 오면 인증/인가 없이도 접근할 수 있음.
                .requestMatchers("/login", "/signup", "/user").permitAll()

                // anyRequest() : 위에서 설정한 url 이외의 요청에 대해 설정함.
                // authenticated() : 별도의 인가는 필요하지 않지만 인증이 성공된 상태여야 접근할 수 있음.
                .anyRequest().authenticated()
                .and()
                .formLogin() // 폼 기반 로그인 설정
                // loginPage() : 로그인 페이지 경로를 설정함.
                // defaultSuccessUrl() : 로그인이 완료되었을 때 이동할 경로를 설정함.
               // .loginPage("/login")
                .defaultSuccessUrl("/articles")
                .and()
                .logout() // 로그아웃 설정
                // logoutSuccessUrl() : 로그아웃이 완료되었을 때 이동할 경로를 설정함.
                // invalidateHttpSession() : 로그아웃 이후에 세션을 전체 삭제할지 여부를 설정함.
                //.logoutSuccessUrl("/login")
                .invalidateHttpSession(true)
                .and()
                .csrf().disable() // csrf 비뢀성화 : CSRF 공격을 방지하기 위해 활성화하는 것이 좋지만 실습을 위해 비활성화함.
                .build();
    }

    // 인증 관리자 관련 설정 : 사용자 정보를 가져올 서비스를 재정의하거나 인증 밥업, 예를 들어 LDAP, JDBC 기반 인증 등을 설정할 때 사용함.
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http,
                                                       BCryptPasswordEncoder bCryptPasswordEncoder, UserDetailService userDetailService)
        throws Exception{
        return http.getSharedObject(AuthenticationManagerBuilder.class)

                // userDetailsService() : 사용자 정보를 가져올 서비스를 설정함. 이때 정의하는 클래스는 반드시 UserDetailsService 를 상속받은 클래스여야 함.
                .userDetailsService(userService) // 사용자 정보 서비스 설정

                // passwordEncoder() : 비밀번호를 암호화하기 위한 인코더를 설정함.
                .passwordEncoder(bCryptPasswordEncoder)
                .and()
                .build();
    }

    // 패스워드 인코더로 사용할 빈 등록
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
