package com.sparta.todo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.sparta.todo.dto.todo.TodoRequestDto;
import com.sparta.todo.dto.todo.TodoResponseDto;
import com.sparta.todo.entity.Todo;
import com.sparta.todo.entity.User;
import com.sparta.todo.repository.TodoRepository;
import com.sparta.todo.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class TodoServiceTest {
    @Mock
    TodoRepository todoRepository;

    @InjectMocks
    TodoService todoService;
    @Mock
    UserRepository userRepository;


    @Test
    @DisplayName("todo 생성 테스트")
    void saveTodo() {
        Long todoId = 1L;
        String title = "title 1";
        String contents = "content 1";
        TodoRequestDto todoRequestDto = new TodoRequestDto(title, contents);

        User user = new User();
        user.setUserId(1L);

        Todo todo = new Todo(todoRequestDto);
        todo.setUser(user);

        given(todoRepository.save(any(Todo.class))).willReturn(todo);

        // when
        TodoResponseDto result = todoService.saveTodo(todoRequestDto, user);

        // then
        assertEquals(title, result.getTitle());
        assertEquals(contents, result.getContents());
    }

    @Test
    @DisplayName("todo 수정 테스트")
    void updateTodo() {
        // given
        Long todoId = 1L;
        String title = "title 1";
        String contents = "content 1";
        TodoRequestDto todoRequestDto = new TodoRequestDto(title, contents);

        User user = new User();
        user.setUserId(1L);

        Todo todo = new Todo(todoRequestDto);
        todo.setUser(user);
        TodoService todoService = new TodoService(todoRepository);

        given(todoRepository.findById(todoId)).willReturn(Optional.of(todo));

        // when
        TodoResponseDto result = todoService.updateTodo(todoId, todoRequestDto, user);

        // then
        assertEquals(title, result.getTitle());
        assertEquals(contents, result.getContents());
    }



}