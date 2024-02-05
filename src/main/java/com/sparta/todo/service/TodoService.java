package com.sparta.todo.service;


import com.sparta.todo.dto.todo.TodoRequestDto;
import com.sparta.todo.dto.todo.TodoListResponseDto;
import com.sparta.todo.dto.todo.TodoResponseDto;
import com.sparta.todo.entity.Todo;
import com.sparta.todo.entity.User;
import com.sparta.todo.jwt.JwtUtil;
import com.sparta.todo.repository.TodoRespository;
import com.sparta.todo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRespository todoRespository;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @Transactional
    public List<TodoResponseDto> saveTodo(TodoRequestDto todoRequestDto, User user ) {

        // RequestDto -> Entity
        Todo todo = new Todo(todoRequestDto, user);
        // DB 저장
        Todo saveTodo = todoRespository.save(todo);
        // Entity -> ResponseDto
        return (List<TodoResponseDto>) new TodoResponseDto(saveTodo, user.getUsername());
    }

    public List<TodoListResponseDto> getTodos() {

        List<Todo> todosList = todoRespository.findAll();
        List<Long> userIdList = todosList.stream().map(todo -> todo.getUser().getUserId()).toList();
        List<User> userList = userRepository.findByIds(userIdList);

        Map<Long, String> usernameMap = userList.stream()
            .collect(Collectors.toMap(User::getUserId, User::getUsername));

         List<TodoListResponseDto> todoResponseDtoList = todosList.stream()
            .map(todo -> {
                String username = usernameMap.get(todo.getUser().getUserId());
                return new TodoListResponseDto(todo, username);
            })
            .collect(Collectors.toList());

         return todoResponseDtoList;
    }

    public Todo getTodoById(Long todoId) {
        return todoRespository.findById(todoId).orElseThrow(() -> new IllegalArgumentException("선택한 할일은 존재하지 않습니다."));
    }

    @Transactional
    public List<TodoResponseDto> updateTodo(Long todoId, TodoRequestDto todoRequestDto, User user) {
        Todo todo = todoRespository.findById(todoId).orElseThrow(() -> new IllegalArgumentException("선택한 메모는 존재하지 않습니다."));
        todo.update(todoRequestDto);
        return (List<TodoResponseDto>) new TodoResponseDto(todo, user.getUsername());
    }

    @Transactional
    public Long deleteTodo(Long todoId) {
        Todo todo = todoRespository.findById(todoId).orElseThrow(() -> new IllegalArgumentException("해당 메모가 없습니다."));
        todoRespository.delete(todo);
        return todoId;
    }
}
