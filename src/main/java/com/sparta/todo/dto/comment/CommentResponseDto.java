package com.sparta.todo.dto.comment;

import com.sparta.todo.common.CommonResponseDto;
import com.sparta.todo.entity.Comment;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Builder
@Setter
public class CommentResponseDto extends CommonResponseDto {
    private Long id;
    private String content;
    private LocalDateTime createdAt;

    public CommentResponseDto(Comment comment){
        this.id = comment.getId();
        this.content = comment.getContent();
        this.createdAt = LocalDateTime.now();
    }


    public CommentResponseDto(String msg, Integer statusCode){
        super(msg, statusCode);
    }
}


