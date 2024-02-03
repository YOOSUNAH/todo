package com.sparta.todo.dto;


import com.sparta.todo.entity.Todo;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TodoResponseDto {
    private Long todoId;
    private String title;
    private String contents;
    private LocalDateTime createdAt;
    private boolean isCompleted;


    public TodoResponseDto(Todo todo){
        this.todoId = todo.getTodoId();
        this.title = todo.getTitle();
        this.contents = todo.getContents();
        this.createdAt = todo.getCreatedAt();
        this.isCompleted = todo.isCompleted();
    }

}
