package com.sparta.test;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.todo.entity.User;
import com.sparta.todo.entity.UserDetailsImpl;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


public class ControllerTest implements CommonTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;
    //ObjectMapper는 Java 객체를 JSON 형식으로 변환하거나, 그 반대로 JSON을 Java 객체로 변환하는 데 사용되는 라이브러리.
    // 주로 객체 직렬화(Serialization) 및 역직렬화(Deserialization)를 수행하는 데에 활용.
    //  JSON 라이브러리에서 제공

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders         // webMvc Test를 하기 위해서 기본적으로 설정해줘야 하는 MvcBuilders
            .webAppContextSetup(context)  // context 정보를 setup해줘야 함.
            .build();

        // Mock 테스트 UserDetails 생성
        UserDetailsImpl testUserDetails = new UserDetailsImpl(TEST_USER);

        // SecurityContext 에 인증된 사용자 설정
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
            testUserDetails, testUserDetails.getPassword(), testUserDetails.getAuthorities()));
    }
}
