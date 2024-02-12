package com.sparta.todo.controller;

import com.sparta.todo.dto.comment.CommentRequestDto;
import com.sparta.todo.dto.comment.CommentResponseDto;
import com.sparta.todo.entity.UserDetailsImpl;
import com.sparta.todo.service.CommentService;
import java.util.concurrent.RejectedExecutionException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comments")
public class CommentController {
    private final CommentService commentService;

    // Post 작성
    // Put {commentId} 수정
    // Delete {commentId} 삭제

    @PostMapping
    public ResponseEntity<CommentResponseDto> createComment(@RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails ){
        CommentResponseDto commentResponseDto = commentService.createComment(commentRequestDto, userDetails.getUser());
        return ResponseEntity.status(201).body(commentResponseDto);
    }

    @PutMapping("{commentId}")
    public ResponseEntity<CommentResponseDto> putComment(@PathVariable Long commentId, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        try {
            CommentResponseDto commentResponseDto = commentService.updateComment(commentId, commentRequestDto, userDetails.getUser());
            return ResponseEntity.status(201).body(commentResponseDto);
        } catch (RejectedExecutionException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommentResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

    @DeleteMapping("{commentId}")
    public ResponseEntity<CommentResponseDto> deleteComment(@PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        CommentResponseDto response = commentService.deleteComment(commentId, userDetails.getUser());
        return ResponseEntity.ok().body(response);
    }
}