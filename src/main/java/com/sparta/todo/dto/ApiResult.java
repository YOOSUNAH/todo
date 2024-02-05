package com.sparta.todo.dto;

import com.sparta.todo.statusEnum.StatusAndEnum;
import lombok.Getter;


@Getter
public class ApiResult {
    private String message;
    private Integer statusCode;

    public ApiResult(StatusAndEnum status) {
        this.message = status.getMessage();
        this.statusCode = status.getStatusCode();
    }
}






