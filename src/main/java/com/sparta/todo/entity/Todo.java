package com.sparta.todo.entity;


import com.sparta.todo.dto.TodoRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
public class Todo {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long todoId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime date;

    private boolean isCompleted;

    public Todo(TodoRequestDto todoRequestDto){
        this.title = todoRequestDto.getTitle();
        this.contents = todoRequestDto.getContents();
        this.date = date;
        this.isCompleted = false;
    }

    public void update(TodoRequestDto todoRequestDto){
        this.title = todoRequestDto.getTitle();
        this.contents = todoRequestDto.getContents();
    }
}




