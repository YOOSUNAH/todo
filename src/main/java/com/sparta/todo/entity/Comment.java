package com.sparta.todo.entity;

import com.sparta.todo.dto.comment.CommentRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @Column
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "todo_id")
    private Todo todo;

    @Column
    private LocalDateTime createDate;

    public Comment(CommentRequestDto dto){
        this.content = dto.getContent();
        this.createDate = LocalDateTime.now();
    }

    public void setUser(User user){
        this.user = user;
    }

    public void setTodo(Todo todo){
        this.todo = todo;
    }

    public void setContent(String content){
        this.content = content;
    }

}
