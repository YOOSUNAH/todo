package com.sparta.todo.filter;

import com.sparta.todo.entity.User;
import com.sparta.todo.jwt.JwtUtil;
import com.sparta.todo.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;

import static com.sparta.todo.jwt.JwtUtil.AUTHORIZATION_HEADER;

@Slf4j(topic = "AuthFilter")
@Component
@Order(2)
public class AuthFilter implements Filter {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    public AuthFilter(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String url = httpServletRequest.getRequestURI();

        if (StringUtils.hasText(url) &&
            (url.startsWith("/api/v1/user") || url.startsWith("/docs"))
        ) {
            log.info("인증 처리를 하지 않는 URL : " + url);
            // 회원가입, 로그인 관련 API 는 인증 필요없이 요청 진행
            chain.doFilter(request, response); // 다음 Filter 로 이동
        } else {
            // 나머지 API 요청은 인증 처리 진행
            // 토큰 확인
            String token = ((HttpServletRequest) request).getHeader(AUTHORIZATION_HEADER);

            if (StringUtils.hasText(token)) { // 토큰이 존재하면 검증 시작
                // JWT 토큰 substring\
                log.info("token value : "+ token);

                // 토큰 검증
                if (jwtUtil.validateToken(token)) {
                    throw new IllegalArgumentException("Token Error");
                }
                // 토큰에서 사용자 정보 가져오기
                Claims info = jwtUtil.getUserInfoFromToken(token);
//                User user = userRepository.findById(Long.valueOf(info.getSubject())).orElseThrow(() ->
//                    new NullPointerException("Not Found User")
//                );

                request.setAttribute("userId", info.getSubject());
                chain.doFilter(request, response); // 다음 Filter 로 이동

            } else {
                throw new IllegalArgumentException("Not Found Token");
            }

        }
    }


}
