package com.sparta.todo.entity;

import com.sparta.todo.dto.comment.CommentRequestDto;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;


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