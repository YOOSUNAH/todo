package com.sparta.todo.entity;


import com.sparta.todo.dto.todo.TodoRequestDto;
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

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    private User user;



    public Todo(TodoRequestDto todoRequestDto, User user){
        this.title = todoRequestDto.getTitle();
        this.contents = todoRequestDto.getContents();
        this.user = user;
        this.isCompleted = false;
    }

    public void update(TodoRequestDto todoRequestDto, Boolean isCompleted){
        this.title = todoRequestDto.getTitle();
        this.contents = todoRequestDto.getContents();
        this.isCompleted = isCompleted;
    }
}


