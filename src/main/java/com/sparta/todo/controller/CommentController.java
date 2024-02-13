package com.sparta.todo.controller;

import com.sparta.todo.common.CommonResponseDto;
import com.sparta.todo.dto.comment.CommentRequestDto;
import com.sparta.todo.dto.comment.CommentResponseDto;
import com.sparta.todo.entity.UserDetailsImpl;
import com.sparta.todo.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.concurrent.RejectedExecutionException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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

    //  추가
    @PostMapping("")
    public ResponseEntity<CommentResponseDto> postComment(@RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info(  "확인좀 헤ㅐ보자으아아아아" +userDetails.getUsername()) ;
        CommentResponseDto responseDto = commentService.createComment(commentRequestDto, userDetails.getUser());
        return ResponseEntity.status(201).body(responseDto);
    }


    //  수정
    @PutMapping("/{commentId}")
    @Operation(summary = "선택 일정 수정", description = "수정하고자 하는 일정의 아이디를 입력해주세요")
    public ResponseEntity<CommonResponseDto> putComment(@PathVariable Long commentId, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            CommentResponseDto responseDto = commentService.updateComment(commentId,
                commentRequestDto, userDetails.getUser());
            return ResponseEntity.ok().body(responseDto);
        } catch (RejectedExecutionException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }

    }

    //  삭제
    @DeleteMapping("/{commentId}")
    @Operation(summary = "선택 일정 삭제", description = "삭제하고자 하는 일정의 아이디를 입력해주세요")
    public ResponseEntity<CommonResponseDto> deleteComment(@PathVariable Long commentId,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            commentService.deleteComment(commentId, userDetails.getUser());
            return ResponseEntity.ok()
                .body(new CommonResponseDto("정상적으로 삭제되었습니다.", HttpStatus.OK.value()));
        } catch (RejectedExecutionException | IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                .body(new CommonResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }
}
