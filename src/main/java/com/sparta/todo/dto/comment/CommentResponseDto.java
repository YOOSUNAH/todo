package com.sparta.todo.dto.comment;

import com.sparta.todo.common.CommonResponseDto;
import com.sparta.todo.dto.user.UserDto;
import com.sparta.todo.entity.Comment;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CommentResponseDto extends CommonResponseDto {
    private Long id;
    private String text;
    private UserDto user;
    private LocalDateTime createdAt;

    public CommentResponseDto(Comment comment){
        this.id = comment.getId();
        this.text = comment.getText();
        this.user = new UserDto(comment.getUser());
        this.createdAt = LocalDateTime.now();
    }



}


