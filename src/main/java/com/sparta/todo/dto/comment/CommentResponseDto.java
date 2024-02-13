package com.sparta.todo.dto.comment;

import com.sparta.todo.common.CommonResponseDto;
import com.sparta.todo.dto.user.UserDto;
import com.sparta.todo.entity.Comment;
import com.sparta.todo.entity.Todo;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
public class CommentResponseDto extends CommonResponseDto {

        private Long commentId;
        private String content;

        private UserDto user;

        private LocalDateTime createDate;
        public CommentResponseDto(Comment comment){
            this.commentId = comment.getCommentId();
            this.content = comment.getContents();
            this.user = new UserDto(comment.getUser());
            this.createDate = LocalDateTime.now();

        }
}


