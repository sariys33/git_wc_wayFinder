package com.wc.wayfinder.config;

import com.wc.wayfinder.security.CustomAccessDeniedHandler;
import com.wc.wayfinder.security.CustomAuthenticationEntryPoint;
import com.wc.wayfinder.security.CustomAuthenticationSuccessHandler;
import com.wc.wayfinder.security.CustomLogoutSuccessHandler;
import com.wc.wayfinder.security.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration // 스프링 환경설정 파일임을 명시. 빈으로 자동 등록
@EnableWebSecurity // 모든 요청 URL 은 스프링 시큐리티의 제어를 받도록 해주는 어노테이션
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService; // rememberMe 할때 필요, 주입받기
    private final DataSource dataSource; // DB 활용 rememberMe 할때 필요
    private final AuthenticationFailureHandler authenticationFailureHandler; //로그인 실패시 알림창
    private final CustomAuthenticationEntryPoint authenticationEntryPoint; // 로그인 안하고 접근시 알림
    private final CustomAuthenticationSuccessHandler authenticationSuccessHandler; // 로그인 성공시 작동
    private final CustomLogoutSuccessHandler logoutSuccessHandler; // 로그아웃 후에 작동
    private final CustomAccessDeniedHandler accessDeniedHandler; // 요청 권한 확인

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.cors().and()
                .authorizeRequests()
                .antMatchers( "/Logis/**", "/layout/**", "/error", "/images/**", "/places/*/show", "/places/*/info", "/places/search", "/main", "/logout" ).permitAll() // 항상 접근 허용
                .antMatchers("/user/signup", "/manager/signup", "/user/login", "/manager/login", "/user/forgotPw", "/manager/forgotPw",
                        "/mail/manager/tempPw", "/mail/user/tempPw", "/user/checkEmail", "/manager/checkEmail", "/mail/authCode").anonymous() // 인증 되지 않은 상태에서만 접근 가능
                .antMatchers("/user/**").hasAuthority("ROLE_USER") // 해당 권한을 가진 사용자만 접근 허용
                .antMatchers("/manager/**", "/places/*/update").hasAuthority("ROLE_MANAGER")
                .antMatchers(HttpMethod.POST, "/login").permitAll() // 로그인 처리 해주는 url. post 방식만 모두 접근 허용
                .antMatchers("/places/*/detail").authenticated() // 인증된 사용자만 허용


                .and()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint) // 로그인안하고 다른 페이지 접근 시 알림 띄우기
                .accessDeniedHandler(accessDeniedHandler) // 권한 필요한 요청 들어오면 작동


                .and()
                .rememberMe()
                .userDetailsService(userDetailsService)
                .tokenRepository(tokenRepository())


                .and()
                .formLogin()  // 로그인 폼 설정
                .loginPage("/user/login") // 사용자 로그인폼 경로
                .loginPage("/manager/login") // 관리자 로그인폼 경로
                .usernameParameter("email") // 로그인 아이디가 넘어오는 파라미터명
                .loginProcessingUrl("/login") // 실제 로그인 처리 URL (POST 요청)
                .successHandler(authenticationSuccessHandler) // 로그인 성공시 작동 할 핸들러
                .failureHandler(authenticationFailureHandler) //로그인 실패시 알림창 띄우기


                .and()
                .logout() // 로그아웃 설정
                .logoutUrl("/logout")
                .invalidateHttpSession(true)
                .deleteCookies("remember-me", "JSESSIONID")
                .logoutSuccessHandler(logoutSuccessHandler)


                .and()
                .csrf().disable(); // csrf 비활성화

        return http.build();
    }

    // AuthenticationManager : 시큐리티 인증 담당 : UserDetailsService 구현 클래스, PasswordEncoder 필요
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // BCryptPassword : 비밀번호 암호화 해주는 클래스 빈으로 등록 (시큐리티에서 비밀번호 암호화 강제함)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 데이터베이스 사용한 remember-me 적용시, DB 직접 접속해서 insert, delete 처리 자동으로해줌
    @Bean
    public PersistentTokenRepository tokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;
    }

}