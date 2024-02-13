package com.sparta.todo.service;


import com.sparta.todo.common.CommonResponseDto;
import com.sparta.todo.dto.comment.CommentRequestDto;
import com.sparta.todo.dto.comment.CommentResponseDto;
import com.sparta.todo.entity.Comment;
import com.sparta.todo.entity.Todo;
import com.sparta.todo.entity.User;
import com.sparta.todo.repository.CommentRepository;
import java.util.concurrent.RejectedExecutionException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final TodoService todoService;

    @Transactional
    public CommentResponseDto createComment(CommentRequestDto commentRequestDto, User user) {
        // todo 있나 확인
        Todo todo = todoService.getTodo(commentRequestDto.getTodoId());

        Comment comment = new Comment(commentRequestDto);
        comment.setUser(user);
        comment.setTodo(todo);

        commentRepository.save(comment);
        return new CommentResponseDto(comment);
    }

    @Transactional
    public CommentResponseDto updateComment(Long commentId, CommentRequestDto commentRequestDto, User user) {
        Comment comment = getComment(commentId, user);
        comment.setContents(commentRequestDto.getContents());
        return new CommentResponseDto(comment);
    }

    @Transactional
    public void deleteComment(Long commentId, User user) {
        Comment comment = getComment(commentId, user);
        commentRepository.delete(comment);
    }

    private Comment getComment(Long commentId, User user) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("Id가 존재하지 않습니다."));
        if(!user.getUserId().equals(comment.getUser().getUserId())){
            throw new RejectedExecutionException("작성자만 수정할 수 있습니다.");
        }
        return comment;
    }
}
