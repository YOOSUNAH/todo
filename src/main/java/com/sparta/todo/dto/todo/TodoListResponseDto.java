package com.sparta.todo.dto.todo;


import com.sparta.todo.dto.user.UserDto;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class TodoListResponseDto {
   private UserDto user;
   private List<TodoResponseDto> todoList;

    public TodoListResponseDto(UserDto user, List<TodoResponseDto> todoList){
      this.user = user;
      this.todoList = todoList;
    }
}
