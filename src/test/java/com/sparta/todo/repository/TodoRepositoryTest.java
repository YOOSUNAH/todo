package com.sparta.todo.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.sparta.todo.entity.Todo;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TodoRepositoryTest {
    @Autowired
    private TodoRepository todoRepository;

    @Test
    public void saveAndFindAllTest() {
        // given
        Todo todo = new Todo();
        todo.setTitle("Test Todo");
        todo.setContent("Test Content");

        // when
        todoRepository.save(todo);

        // then
        List<Todo> todos = todoRepository.findAll();
        assertEquals(1, todos.size());
        Todo savedTodo = todos.get(0);
        assertEquals("Test Todo", savedTodo.getTitle());
        assertEquals("Test Contents", savedTodo.getContent());
    }
}