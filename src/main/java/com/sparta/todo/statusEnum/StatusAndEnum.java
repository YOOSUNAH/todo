package com.sparta.todo.statusEnum;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public enum StatusAndEnum {
    SIGNUP_SUCCESS("회원 가입에 성공했습니다.",200),
    LOGIN_SUCCESS( "로그인에 성공했습니다. (토큰을 생성 완료)", 200);

    String message;
    Integer statusCode;
    StatusAndEnum(String message, Integer statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }

}