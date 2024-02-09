package com.sparta.todo.dto.todo;


import com.sparta.todo.common.CommonResponseDto;
import com.sparta.todo.entity.Todo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import lombok.Setter;


@Getter
@Builder
@Setter
@AllArgsConstructor
public class TodoResponseDto extends CommonResponseDto {
    private Long todoId;
    private String title;
    private String contents;
    private String username;
    private LocalDateTime createdAt;

    public TodoResponseDto(Todo todo, String username){
        this.todoId = todo.getTodoId();
        this.title = todo.getTitle();
        this.contents = todo.getContents();
        this.username = username;
        this.createdAt = todo.getCreatedAt();
    }


    public TodoResponseDto(String msg, Integer statusCode) {
        super(msg, statusCode);
    }

}
