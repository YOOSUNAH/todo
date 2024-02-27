package com.sparta.todo.entity;

import com.sparta.todo.dto.todo.TodoRequestDto;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@EqualsAndHashCode
public class Todo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long todoId;

    @Column
    private String title;

    @Column
    private String content;

    @Column
    private LocalDateTime createDate;

    @Column
    private Boolean isCompleted;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "todo")
    private List<Comment> comments;

    @Builder
    public Todo(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Todo(TodoRequestDto dto) {
        this.title = dto.getTitle();
        this.content = dto.getContents();
        this.createDate = LocalDateTime.now();
        this.isCompleted = false;
    }

    // 연관관계 메서드
    public void setUser(User user) {
        this.user = user;
    }

    // 서비스 메서드
    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void complete() {
        this.isCompleted = true;
    }
}


