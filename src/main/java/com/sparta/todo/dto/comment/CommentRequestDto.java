package com.sparta.todo.dto.comment;

import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequestDto {
    private Long todoId;

    @NotBlank(message = "내용을 입력해주세요.")
    private String contents;
}
