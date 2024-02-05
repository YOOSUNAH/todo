package com.sparta.todo.config;

import com.sparta.todo.filter.AuthFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableWebSecurity // Spring Security 지원을 가능하게 함
public class WebSecurityConfig implements WebMvcConfigurer {

    private final AuthFilter authFilter;

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
                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**").permitAll() // swagger 접근 허용 설정
                .requestMatchers("/api/v1/user/**").permitAll() // '/api/user/'로 시작하는 요청 모두 접근 허가
                .requestMatchers("/api/v1/todos/**").permitAll() // '/api/user/'로 시작하는 요청 모두 접근 허가
                .anyRequest().authenticated() // 그 외 모든 요청 인증처리
        );

        http.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);
        // 접근 불가 페이지
        http.exceptionHandling((exceptionHandling) ->
            exceptionHandling
                // "접근 불가" 페이지 URL 설정
                .accessDeniedPage("/forbidden.html")
        );

        return http.build();
    }
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new UserHandlerMethodArgumentResolver());
    }
}


