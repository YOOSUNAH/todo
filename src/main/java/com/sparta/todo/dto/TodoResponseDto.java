package com.sparta.todo.dto;


import com.sparta.todo.entity.Todo;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TodoResponseDto {
    private Long todoId;
    private String title;
    private String contents;
    private LocalDateTime date;
    private boolean isCompleted;


    public TodoResponseDto(Todo todo){
        this.todoId = todo.getTodoId();
        this.title = todo.getTitle();
        this.contents = todo.getContents();
        this.date = todo.getDate();
        this.isCompleted = todo.isCompleted();
    }

}
