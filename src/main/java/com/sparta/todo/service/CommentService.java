package com.sparta.todo.service;

import com.sparta.todo.dto.comment.CommentRequestDto;
import com.sparta.todo.dto.comment.CommentResponseDto;
import com.sparta.todo.dto.todo.TodoResponseDto;
import com.sparta.todo.entity.Comment;
import com.sparta.todo.entity.Todo;
import com.sparta.todo.entity.User;
import com.sparta.todo.repository.CommentRespository;
import java.util.concurrent.RejectedExecutionException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRespository commentRespository;
    private final TodoService todoService;

    public CommentResponseDto createComment(CommentRequestDto commentRequestDto, User user) {
            Todo todo = todoService.getTodo(commentRequestDto.getTodoId());

            Comment comment = new Comment(commentRequestDto);
            comment.setUser(user);
            comment.setTodo(todo);

            commentRespository.save(comment);
            return new CommentResponseDto(comment);
    }

    @Transactional
    public CommentResponseDto updateComment(Long commentId, CommentRequestDto commentRequestDto, User user) {
        Comment comment = getUserComment(commentId, user);
       comment.setText(commentRequestDto.getText());
        return new CommentResponseDto(comment);
    }
    @Transactional
    public void deleteComment(Long commentId, User user) {
        Comment comment = getUserComment(commentId, user);
        commentRespository.delete(comment);
    }

    private Comment getUserComment(Long commentId, User user) {
        Comment comment = commentRespository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 Id입니다."));
        if(!user.getUserId().equals(comment.getUser().getUserId())){
            throw new RejectedExecutionException("작성자만 수정할 수 있습니다.");
        }
        return comment;
    }
}


