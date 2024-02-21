package com.sparta.todo.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.sparta.todo.entity.UserDetailsImpl;
import org.antlr.v4.runtime.Token;
import org.mockito.Mock;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.todo.dto.comment.CommentRequestDto;
import com.sparta.todo.dto.comment.CommentResponseDto;
import com.sparta.todo.entity.Comment;
import com.sparta.todo.entity.Todo;
import com.sparta.todo.entity.User;
import com.sparta.todo.jwt.JwtUtil;
import com.sparta.todo.repository.CommentRepository;
import com.sparta.todo.repository.TodoRepository;
import com.sparta.todo.service.CommentService;
import java.lang.reflect.Field;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@WebMvcTest(controllers = MemberController.class,
//		excludeFilters = {
//	    @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebMvcConfigurer.class)})


@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(controllers = CommentController.class,
    excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebMvcConfigurer.class)})
class CommentControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
            .webAppContextSetup(context)
            .apply(springSecurity())
            .build();
    }

    // security 설정
    @Autowired
    ObjectMapper objectMapper; //  Java 객체를 JSON으로 변환하거나 JSON을 Java 객체로 역직렬화하는데 사용

    @MockBean
    TodoRepository todoRepository;

    @Mock
    StringUtils stringUtils;


    @MockBean
    CommentRepository commentRepository;

    @MockBean
    JwtUtil jwtUtil;

    @MockBean
    CommentService commentService;

    @Test
    @DisplayName("댓글 추가 테스트")
    @WithMockUser
    void postComment() throws Exception {
        // given
        CommentRequestDto commentRequestDto = new CommentRequestDto();
        commentRequestDto.setTodoId(1L);
        commentRequestDto.setContents("테스트 댓글");
        Field field = User.class.getDeclaredField("userId");
        field.setAccessible(true);
        User user = new User();
        field.set(user, 1L);
        Todo todo = new Todo();
        todo.setUser(user);
        todo.setContent("content");
        todo.setTitle("title");
        Comment comment = new Comment();
        comment.setTodo(todo);
        comment.setUser(user);
        comment.setContents("content");

        UserDetails mockUserDetails = mock(UserDetailsImpl.class);
        when(mockUserDetails.getUsername()).thenReturn("anotherMockUsername");

       String token = jwtUtil.createToken(user.getUserId());

        when(todoRepository.findById(1L))
            .thenReturn(Optional.of(
                todo));    // 어떤 CommentRequestDto와 어떤 사용자(any())를 인자로 전달하더라도 항상 responseDto를 반환하도록 설정한 Mock 객체를 만들기

        when(commentRepository.save(any()))
            .thenReturn(comment);

        when(commentService.createComment(commentRequestDto, user))
            .thenReturn(new CommentResponseDto(comment));

        //when
//        CommentResponseDto responseDto = commentService.createComment(commentRequestDto, user);

        // then

        mvc.perform(post("/api/v1/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .content(objectMapper.writeValueAsString(commentRequestDto)))
                .andExpect(status().isOk());
//                .andExpect(status().isCreated());
    }

    @Test
    void putComment() throws Exception {
//        // given
//        Long commentId = 1L;
//        CommentRequestDto requestDto = new CommentRequestDto();
//        CommentResponseDto responseDto = new CommentResponseDto(new Comment());
//        when(commentService.updateComment(any(Long.class), any(CommentRequestDto.class), any()))
//            .thenReturn(responseDto);
//
//        // when
//        ResultActions resultActions = mockMvc.perform(put("/api/v1/comments/{commentId}", commentId)
//            .contentType(MediaType.APPLICATION_JSON);
//
//        // then
//        resultActions.andExpect(status().isOk())
//            .andExpect((ResultMatcher) jsonPath("$.content").value("Updated comment"));
    }

    @Test
    void deleteComment() throws Exception {

    }
}