package com.sparta.todo.controller;

import com.sparta.todo.dto.todo.TodoListResponseDto;
import com.sparta.todo.dto.todo.TodoRequestDto;
import com.sparta.todo.dto.todo.TodoResponseDto;
import com.sparta.todo.dto.user.UserDto;
import com.sparta.todo.entity.UserDetailsImpl;
import com.sparta.todo.service.TodoService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.RejectedExecutionException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/todos")
public class TodoController {

    private final TodoService todoService;

    // 일정 추가
    @PostMapping("")
    @Operation(summary = "일정 추가", description = "제목, 내용을 입력해주세요")
    public ResponseEntity<TodoResponseDto> postTodo(@RequestBody TodoRequestDto todoRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        TodoResponseDto todoResponseDto = todoService.saveTodo(todoRequestDto, userDetails.getUser());
        return ResponseEntity.ok().body(todoResponseDto);
    }

    // 일정 목록 조회
    @GetMapping
    public ResponseEntity<List<TodoListResponseDto>> getTodoList() {
        List<TodoListResponseDto> response = new ArrayList<>();

        Map<UserDto, List<TodoResponseDto>> responseDTOMap = todoService.getUserTodoMap();

        responseDTOMap.forEach((key, value) -> response.add(new TodoListResponseDto(key, value)));

        return ResponseEntity.ok().body(response);
    }


    // 선택 일정 조회
    @GetMapping("/{todoId}")
    @Operation(summary = "선택 일정 조회",  description = "조회하고자 하는 일정의 아이디를 입력해주세요")
    public ResponseEntity<TodoResponseDto> getTodoById(@PathVariable Long todoId) {
        try {
            TodoResponseDto todoResponseDto = todoService.getTodoById(todoId);
            return ResponseEntity.status(201).body(todoResponseDto);
        } catch (RejectedExecutionException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new TodoResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }

    }

    // 선택 일정 수정
    @PutMapping("/{todoId}")
    @Operation(summary = "선택 일정 수정", description = "수정하고자 하는 일정의 아이디를 입력해주세요")
    public ResponseEntity<TodoResponseDto> putTodo(@PathVariable Long todoId, @RequestBody TodoRequestDto todoRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            TodoResponseDto todoResponseDto = todoService.updateTodo(todoId,todoRequestDto, userDetails.getUser());
            return ResponseEntity.ok().body(todoResponseDto);
        } catch (RejectedExecutionException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new TodoResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

    // 선택 일정 수정
    @PatchMapping("/{todoId}/complete")
    @Operation(summary = "선택 일정 수정", description = "수정하고자 하는 일정의 아이디를 입력해주세요")
    public ResponseEntity<TodoResponseDto> completeTodo(@PathVariable Long todoId,@AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            TodoResponseDto todoResponseDto = todoService.completeTodo(todoId,userDetails.getUser());
            return ResponseEntity.ok().body(todoResponseDto);
        } catch (RejectedExecutionException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new TodoResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

    // 선택 일정 삭제
    @DeleteMapping("/{todoId}")
    @Operation(summary = "선택 일정 삭제", description = "삭제하고자 하는 일정의 아이디를 입력해주세요")
    public void deleteTodo(@PathVariable Long todoId) {
       todoService.deleteTodo(todoId);
    }
}