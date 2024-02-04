package com.sparta.todo.dto.user;

import com.sparta.todo.dto.ApiResult;
import com.sparta.todo.entity.User;
import lombok.Getter;

@Getter
public class LoginResponseDto {
    private final Long userId;
    private final String token;
    private final String message;
    private Integer statusCode;

    public LoginResponseDto(User user, String token, ApiResult apiResult){
        this.userId = user.getUserId();
        this.token = token;
        this.message = apiResult.getMessage();
        this.statusCode = apiResult.getStatusCode();
    }
}
