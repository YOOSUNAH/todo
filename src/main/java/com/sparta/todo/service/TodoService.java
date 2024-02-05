package com.sparta.todo.service;

import com.sparta.todo.dto.todo.TodoRequestDto;
import com.sparta.todo.dto.todo.TodoListResponseDto;
import com.sparta.todo.dto.todo.TodoResponseDto;
import com.sparta.todo.entity.Todo;
import com.sparta.todo.entity.User;
import com.sparta.todo.jwt.JwtUtil;
import com.sparta.todo.repository.TodoRespository;
import com.sparta.todo.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRespository todoRespository;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @Transactional
    public TodoResponseDto saveTodo(HttpServletRequest request, TodoRequestDto todoRequestDto) {
        String token = jwtUtil.getJwtFromHeader(request);
        // token -> userId
        Claims info = jwtUtil.getUserInfoFromToken(token);
        User user = userRepository.findById(Long.valueOf(info.getSubject())).orElseThrow(() ->
            new NullPointerException("Not Found User")
        );
        // RequestDto -> Entity
        Todo todo = new Todo(todoRequestDto, user);
        // DB 저장
        Todo saveTodo = todoRespository.save(todo);
        // Entity -> ResponseDto

        return new TodoResponseDto(saveTodo, user.getUsername());

    }


    public List<TodoListResponseDto> getTodos() {
        List<Todo> todosList = todoRespository.findAll(Sort.by("createdAt").descending());
        List<Long> userIdList = todosList.stream().map(todo -> todo.getUser().getUserId()).toList();
        List<User> userList = userRepository.findByIds(userIdList);

        Map<Long, String> usernameMap = userList.stream().collect(Collectors.toMap(User::getUserId, User::getUsername));

        return todosList.stream()
            .map(todo -> {
                String username = usernameMap.get(todo.getUser().getUserId());
                return new TodoListResponseDto(todo, username);
            })
            .collect(Collectors.toList());
    }

    public TodoResponseDto getTodoById(Long todoId) {
        Todo todo = todoRespository.findById(todoId).orElseThrow(() -> new IllegalArgumentException("해당 할일은 없습니다."));
        return new TodoResponseDto(todo, todo.getUser().getUsername());
    }

    @Transactional
    public TodoResponseDto updateTodo(Long todoId, TodoRequestDto todoRequestDto, User user) {
        Todo todo = todoRespository.findById(todoId).orElseThrow(() -> new IllegalArgumentException("선택한 할일은 존재하지 않습니다."));
        todo.update(todoRequestDto);
        return new TodoResponseDto(todo, user.getUsername());
    }

    @Transactional
    public Long deleteTodo(Long todoId) {
        Todo todo = todoRespository.findById(todoId).orElseThrow(() -> new IllegalArgumentException("해당 할일은 없습니다."));
        todoRespository.delete(todo);
        return todoId;
    }
}
