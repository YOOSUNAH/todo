package com.sparta.todo.service;


import com.sparta.todo.dto.todo.TodoRequestDto;
import com.sparta.todo.dto.todo.TodoResponseDto;
import com.sparta.todo.entity.Todo;
import com.sparta.todo.jwt.JwtUtil;
import com.sparta.todo.repository.TodoRespository;
import io.jsonwebtoken.Jwt;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRespository todoRespository;
    private final JwtUtil jwtUtil;

    @Transactional
    public List<TodoResponseDto>  saveTodo(TodoRequestDto todoRequestDto, String token, HttpServletRequest res) {
        // header에서 가져오기
        jwtUtil.getJwtFromHeader(res);
        // 검증
         jwtUtil.validateToken(token);


        // RequestDto -> Entity
        Todo todo = new Todo(todoRequestDto);
        // DB 저장
        Todo saveTodo = todoRespository.save(todo);
        // Entity -> ResponseDto
        return (List<TodoResponseDto>) new TodoResponseDto(saveTodo);

    }

    public List<TodoResponseDto> getTodos() {
        return todoRespository.findAll().stream().map(TodoResponseDto::new).toList();
    }

    public Todo getTodoById(Long todoId) {
        return todoRespository.findById(todoId).orElseThrow(() -> new IllegalArgumentException("선택한 할일은 존재하지 않습니다."));
    }

    @Transactional
    public Long updateTodo(Long todoId, TodoRequestDto todoRequestDto) {
        Todo todo = todoRespository.findById(todoId).orElseThrow(() -> new IllegalArgumentException("선택한 메모는 존재하지 않습니다."));
        todo.update(todoRequestDto);
        return todoId;
    }

    @Transactional
    public Long deleteTodo(Long todoId) {
        Todo todo = todoRespository.findById(todoId).orElseThrow(() -> new IllegalArgumentException("해당 메모가 없습니다."));
        todoRespository.delete(todo);
        return todoId;
    }
}
