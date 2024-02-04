package com.sparta.todo.controller;


import com.sparta.todo.dto.todo.TodoRequestDto;
import com.sparta.todo.dto.todo.TodoResponseDto;
import com.sparta.todo.entity.Todo;
import com.sparta.todo.service.TodoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class TodoController {

    private final TodoService todoService;

    // 일정 추가
    @PostMapping("/todos")
    @Operation(
        summary = "일정 추가",
        description = "제목, 내용을 입력해주세요",
        responses = {
            @ApiResponse(responseCode = "200", description = "일정 추가 성공"),
            @ApiResponse(responseCode = "500", description = "일정 추가 에러")
        }
    )
    public List<TodoResponseDto>  saveTodo(@RequestBody TodoRequestDto todoRequestDto, String token, HttpServletRequest res) {
        return todoService.saveTodo(todoRequestDto, token, res);
    }

    // 일정 목록 조회
    @GetMapping("/todos")
    @Operation(
        summary = "일정 목록 조회",
        description = "전체 일정 목록을 조회해줍니다"
    )
    public List<TodoResponseDto> getTodos() {
        return todoService.getTodos();
    }

    // 선택 일정 조회
    @GetMapping("/todos/{todoId}")
    @Operation(
        summary = "선택 일정 조회",
        description = "조회하고자 하는 일정의 아이디를 입력해주세요"
    )
    public Todo getTodoById(@PathVariable Long todoId) {
        return todoService.getTodoById(todoId);
    }
    // 선택 일정 수정
    @PutMapping("/todos/{todoId}")
    @Operation(
        summary = "선택 일정 수정",
        description = "수정하고자 하는 일정의 아이디를 입력해주세요"
    )
    public Long updateMemo(@PathVariable Long todoId, @RequestBody TodoRequestDto todoRequestDto){
        return todoService.updateTodo(todoId, todoRequestDto);
    }

    // 선택 일정 삭제
    @DeleteMapping("/todos/{todoId}")
    @Operation(
        summary = "선택 일정 삭제",
        description = "삭제하고자 하는 일정의 아이디를 입력해주세요"
    )
    public Long deleteTodo(@PathVariable Long todoId) {
        return todoService.deleteTodo(todoId);
    }
}