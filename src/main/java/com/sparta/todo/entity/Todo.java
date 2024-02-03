package com.sparta.todo.entity;


import com.sparta.todo.dto.TodoRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Todo extends Timestamped {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long todoId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    private boolean isCompleted;

    public Todo(TodoRequestDto todoRequestDto){
        this.title = todoRequestDto.getTitle();
        this.contents = todoRequestDto.getContents();
        this.isCompleted = false;
    }

    public void update(TodoRequestDto todoRequestDto){
        this.title = todoRequestDto.getTitle();
        this.contents = todoRequestDto.getContents();
        this.isCompleted = true;
    }
}




