package com.sparta.todo.dto;

import com.sparta.todo.statusEnum.StatusEnum;
import lombok.Getter;


@Getter
public class ApiResult {
    private String message;
    private Integer statusCode;

    public ApiResult(StatusEnum status) {
        this.message = status.getMessage();
        this.statusCode = status.getStatusCode();
    }
}






