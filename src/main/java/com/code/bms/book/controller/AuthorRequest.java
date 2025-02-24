package com.code.bms.book.controller;

import jakarta.validation.constraints.NotBlank;

public record AuthorRequest(
        @NotBlank(message = "저자 이름은 필수 입력 값입니다.")
        String name,
        @NotBlank(message = "이메일은 필수 입력 값입니다.")
        String email) {
}
