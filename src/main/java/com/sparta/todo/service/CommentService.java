package com.sparta.todo.service;

import com.sparta.todo.dto.comment.CommentRequestDto;
import com.sparta.todo.dto.comment.CommentResponseDto;
import com.sparta.todo.entity.User;
import com.sparta.todo.repository.CommentRespository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRespository commentRespository;
    private final TodoService todoService;


    @Transactional
    public CommentResponseDto createComment(CommentRequestDto commentRequestDto, User user) {

    }

    public CommentResponseDto updateComment(Long commentId, CommentRequestDto commentRequestDto,
        User user) {

    }
    public CommentResponseDto deleteComment(Long commentId, User user) {

    }
}


