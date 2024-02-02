package com.sparta.todo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;


@Configuration
@EnableWebSecurity // Spring Security 지원을 가능하게 함
public class WebConfig {
        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            // CSRF 설정
            http.csrf((csrf) -> csrf.disable());

            // 기본 설정인 Session 방식은 사용하지 않고 JWT 방식을 사용하기 위한 설정
            http.sessionManagement((sessionManagement) ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            );

            http.authorizeHttpRequests((authorizeHttpRequests) ->
                authorizeHttpRequests
                    .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll() // resources 접근 허용 설정
                    .requestMatchers("/api/user/**").permitAll() // '/api/user/'로 시작하는 요청 모두 접근 허가
                    .requestMatchers("/api/todos/**").permitAll() // '/api/user/'로 시작하는 요청 모두 접근 허가
                    .anyRequest().authenticated() // 그 외 모든 요청 인증처리
            );


            // 접근 불가 페이지
            http.exceptionHandling((exceptionHandling) ->
                exceptionHandling
                    // "접근 불가" 페이지 URL 설정
                    .accessDeniedPage("/forbidden.html")
            );

            return http.build();
        }
    }


