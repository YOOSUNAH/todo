package com.sparta.todo.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.todo.controller.CommentController;
import com.sparta.todo.dto.comment.CommentRequestDto;
import com.sparta.todo.dto.comment.CommentResponseDto;
import com.sparta.todo.entity.Comment;
import com.sparta.todo.entity.Todo;
import com.sparta.todo.entity.User;
import com.sparta.todo.entity.UserDetailsImpl;
import com.sparta.todo.jwt.JwtUtil;
import com.sparta.todo.service.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@WebMvcTest(CommentController.class)
@MockBean(JpaMetamodelMappingContext.class)
public class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private CommentService commentService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setup() {
        User mockUser = mock(User.class);
        UserDetailsImpl mockUserDetails = mock(UserDetailsImpl.class);
        given(mockUserDetails.getUser()).willReturn(mockUser);

        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(mockUserDetails, null));

        mockMvc = webAppContextSetup(context).build();
    }

    @Test
    @WithMockUser
    public void postComment_ShouldCreateComment() throws Exception {
        Comment comment = new Comment();
//        comment.setId(1L);
        comment.setTodo(new Todo());
        comment.setUser(new User());

        CommentRequestDto commentRequestDto = new CommentRequestDto();
        CommentResponseDto commentResponseDto = new CommentResponseDto(comment);

        given(commentService.createComment(any(CommentRequestDto.class), any(User.class)))
            .willReturn(commentResponseDto);

        mockMvc.perform(post("/api/v1/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(commentRequestDto)))
            .andExpect(status().isCreated())
            .andExpect(content().json(objectMapper.writeValueAsString(commentResponseDto)));
    }

}