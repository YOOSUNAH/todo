package com.sparta.todo.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.todo.config.WebSecurityConfig;
import com.sparta.todo.dto.todo.TodoRequestDto;
import com.sparta.todo.entity.User;
import com.sparta.todo.entity.UserDetailsImpl;
import com.sparta.todo.mvc.MockSpringSecurityFilter;
import com.sparta.todo.service.TodoService;
import com.sparta.todo.service.UserService;
import java.security.Principal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest(
    controllers = {UserController.class, TodoController.class},
    // 제외할 filter
    excludeFilters = {
        @ComponentScan.Filter(
            type = FilterType.ASSIGNABLE_TYPE,
            classes = WebSecurityConfig.class  //  우리가 만든 WebSecurityConfig
        )
    }
)
class UserTodoControllerTest {

    private MockMvc mvc;
    private Principal mockPrincipal;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    UserService userService;

    @MockBean
    TodoService todoService;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
            .apply(springSecurity(new MockSpringSecurityFilter()))
            .build();
    }


    private void mockUserSetup() {
        // Mock 테스트 유져 생성
        String username = "username";
        String password = "password12";
        User testUser = new User(username, password);
        UserDetailsImpl testUserDetails = new UserDetailsImpl(testUser);
        mockPrincipal = new UsernamePasswordAuthenticationToken(testUserDetails, "", testUserDetails.getAuthorities());
    }


    @Test
    @DisplayName("로그인")
    void login() throws Exception {
        mvc.perform(post("/api/v1/user/login"))
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("회원 가입 요청 처리")
    void signup() throws Exception {
        // given
        MultiValueMap<String, String> signupRequestForm = new LinkedMultiValueMap<>();
        signupRequestForm.add("username", "username");
        signupRequestForm.add("password", "password12");

        // when - then
        mvc.perform(post("/api/v1/user/signup")
                .params(signupRequestForm)
            )
            .andExpect(status().is3xxRedirection());
    }
    @Test
    @DisplayName("todo 등록")
    void postTodo() throws Exception {
        // given
        this.mockUserSetup();
        String title = "title";
        String content = "content";
        TodoRequestDto requestDto = new TodoRequestDto(
            title,
            content
        );

        String postInfo = objectMapper.writeValueAsString(requestDto);

        // when - then
        mvc.perform(post("/api/v1/todos")
                .content(postInfo)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .principal(mockPrincipal)
            )
            .andExpect(status().isOk());

    }
}