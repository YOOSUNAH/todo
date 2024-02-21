package com.sparta.todo.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.put;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.todo.dto.comment.CommentRequestDto;
import com.sparta.todo.dto.comment.CommentResponseDto;
import com.sparta.todo.entity.Comment;
import com.sparta.todo.entity.UserDetailsImpl;
import com.sparta.todo.service.CommentService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;


@WebMvcTest(CommentController.class)
class CommentControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper; //  Java 객체를 JSON으로 변환하거나 JSON을 Java 객체로 역직렬화하는데 사용

    @Autowired
    UserDetailsImpl userDetails;

    @MockBean
    CommentService commentService;

    @Test
    @DisplayName("댓글 추가 테스트")
    void postComment() throws Exception {
        // given
        CommentRequestDto commentRequestDto = new CommentRequestDto();
        commentRequestDto.setContents("테스트 댓글");

        //when
        CommentResponseDto responseDto = commentService.createComment(commentRequestDto, userDetails.getUser());
        when(commentService.createComment(any(CommentRequestDto.class), any()))
            .thenReturn(responseDto);    // 어떤 CommentRequestDto와 어떤 사용자(any())를 인자로 전달하더라도 항상 responseDto를 반환하도록 설정한 Mock 객체를 만들기

        // then
        ResultActions resultActions = mvc.perform((RequestBuilder) post("/api/v1/comments"))
                                         .andExpect(status().isCreated());
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