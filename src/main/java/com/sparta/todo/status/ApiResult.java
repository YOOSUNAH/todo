package com.sparta.todo.status;

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






