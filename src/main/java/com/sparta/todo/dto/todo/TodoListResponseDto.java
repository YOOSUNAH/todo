package com.sparta.todo.dto.todo;


import com.sparta.todo.entity.Todo;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TodoListResponseDto {
    private Long todoId;
    private String title;
    private String username;
    private LocalDateTime createdAt;
    private boolean isCompleted;



    public TodoListResponseDto(Todo todo, String username){
        this.todoId = todo.getTodoId();
        this.title = todo.getTitle();
        this.username = username;
        this.createdAt = todo.getCreatedAt();
        this.isCompleted = todo.isCompleted();

    }


}
