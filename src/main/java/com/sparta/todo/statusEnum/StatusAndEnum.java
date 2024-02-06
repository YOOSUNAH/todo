package com.sparta.todo.statusEnum;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public enum StatusAndEnum {
    SIGNUP_SUCCESS("회원 가입에 성공했습니다.",200),
    LOGIN_SUCCESS( "로그인에 성공했습니다. (토큰을 생성 완료)", 200),
    DUPLICATED_USERNAME("중복된 username 입니다.", 400),


    INVALID_TOKEN("토큰이 유효하지 않습니다.", 400),
    NOT_RIGHT_USER("작성자만 삭제/수정할 수 있습니다.", 400),
    NOT_FOUND_USER("회원을 찾을 수 없습니다.", 400);


    String message;
    Integer statusCode;
    StatusAndEnum(String message, Integer statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }

}