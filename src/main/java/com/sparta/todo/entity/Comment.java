package com.sparta.todo.entity;

import com.sparta.todo.dto.comment.CommentRequestDto;
import com.sparta.todo.dto.todo.TodoRequestDto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Entity
@NoArgsConstructor
@Table(name = "Comment_table")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long CommentId;

    @Column(nullable = false)
    private String contents;

    @Column
    private LocalDateTime createDate;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    @ManyToOne
    @JoinColumn(name = "todo_id")
    private Todo todo;

    public Comment(CommentRequestDto requestDto){
      this.contents = requestDto.getContents();
      this.createDate = LocalDateTime.now();

    }

    public void setUser(User user){
        this.user = user;
    }

    public void setTodo(Todo todo){
        this.todo = todo;
    }

    public void setContents(String contents){
        this.contents = contents;
    }
}