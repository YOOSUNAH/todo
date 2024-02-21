package com.sparta.todo.dto.todo;


import com.sparta.todo.common.CommonResponseDto;
import com.sparta.todo.dto.user.UserDto;
import com.sparta.todo.entity.Todo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;
import lombok.Setter;


@Getter
@Builder
@Setter
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class TodoResponseDto extends CommonResponseDto {
    private Long todoId;
    private String title;
    private String contents;
    private Boolean isCompleted;
    private UserDto user;
    private LocalDateTime createdAt;

    public TodoResponseDto(Todo todo){
        this.todoId = todo.getTodoId();
        this.title = todo.getTitle();
        this.contents = todo.getContent();
        this.isCompleted = todo.getIsCompleted();
        this.user = new UserDto(todo.getUser());
        this.createdAt = todo.getCreateDate();
    }


    public TodoResponseDto(String msg, Integer statusCode) {
        super(msg, statusCode);
    }

}
