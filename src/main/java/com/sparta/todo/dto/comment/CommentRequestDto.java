package com.sparta.todo.dto.comment;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequestDto {
    private Long todoId;
    private String content;
}
