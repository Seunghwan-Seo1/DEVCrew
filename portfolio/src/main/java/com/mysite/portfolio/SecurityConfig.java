package com.mysite.portfolio;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
                .requestMatchers(new AntPathRequestMatcher("/admin/**")).hasRole("ADMIN")  // 관리자 페이지 접근 제한
                .requestMatchers(new AntPathRequestMatcher("/**")).permitAll())  // 그 외 경로는 모두 허용
            .csrf((csrf) -> csrf
                    .ignoringRequestMatchers(new AntPathRequestMatcher("/**")))
            .formLogin((formLogin) -> formLogin
                    .loginPage("/signin")
                    .defaultSuccessUrl("/"))
            .logout((logout) -> logout
                    .logoutRequestMatcher(new AntPathRequestMatcher("/signout"))
                    .logoutSuccessUrl("/")
                    .invalidateHttpSession(true))
            .exceptionHandling((exceptionHandling) -> exceptionHandling
                    .accessDeniedHandler(accessDeniedHandler()))  // 권한 없는 사용자의 접근 처리
            ;
        return http.build();
    }

    // 권한이 없는 사용자가 접근할 때 index 페이지로 리다이렉트
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            response.sendRedirect("/");  // index 페이지로 리다이렉트
        };
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    
   

}
