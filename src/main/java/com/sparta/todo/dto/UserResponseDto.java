package com.sparta.todo.dto;

import com.sparta.todo.entity.User;
import lombok.Getter;

@Getter
public class UserResponseDto {
    private Long userId;
    private String username;
    private String password;


    public UserResponseDto(User user){
        this.userId = user.getUserId();
        this.username = user.getUsername();
        this.password = user.getPassword();
    }
}
