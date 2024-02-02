package com.sparta.todo.controller;


import com.sparta.todo.dto.TodoRequestDto;
import com.sparta.todo.dto.TodoResponseDto;
import com.sparta.todo.entity.Todo;
import com.sparta.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TodoController {
    private final TodoService todoService;

    // 일정 추가
    @PostMapping("/todos")
    public TodoResponseDto saveTodo(@RequestBody TodoRequestDto todoRequestDto) {
        return todoService.saveTodo(todoRequestDto);
    }

    // 일정 목록 조회
    @GetMapping("/todos")
    public List<TodoResponseDto> getTodos() {
        return todoService.getTodos();
    }

    // 선택 일정 조회
    @GetMapping("/todos/{todoId}")
    public Todo getTodoById(@PathVariable Long todoId) {
        return todoService.getTodoById(todoId);
    }
    // 선택 일정 수정
    @PutMapping("/todo/{id}")
    public Long updateMemo(@PathVariable Long todoId, @RequestBody TodoRequestDto todoRequestDto){
        return todoService.updateTodo(todoId, todoRequestDto);
    }

    // 선택 일정 삭제
    @DeleteMapping("/todos/{todoId}")
    public Long deleteTodo(@PathVariable Long todoId) {
        return todoService.deleteTodo(todoId);
    }
}