package com.sparta.todo.dto.user;

import com.sparta.todo.entity.User;
import lombok.Getter;

@Getter
public class LoginResponseDto {
    private final Long userId;
    private final String username;
    private final String password;
    private final String token;

    public LoginResponseDto(User user, String token){
        this.userId = user.getUserId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.token = token;
    }
}
