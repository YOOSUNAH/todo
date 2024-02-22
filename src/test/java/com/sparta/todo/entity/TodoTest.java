package com.sparta.todo.entity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class TodoTest {

    @Test
    @DisplayName("완료 여부 확인")
    void complete() {
        // given
         //build 또는 tool (reflection test util(dto에 작업하지 않아도 된다.)
        Todo todo = Todo.builder()
            .title("Test")
            .content("Test")
            .build();

        // when
        todo.complete();

        // then
        assertTrue(todo.getIsCompleted());

    }
}