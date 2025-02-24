package com.code.bms.book.controller;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record BookRequest(
        @NotBlank(message = "제목은 필수 입력 값입니다.")
        String title,
        String description,
        @NotBlank(message = "도서번호는 필수 입력 값입니다.")
        String isbn,
        LocalDate publicationDate,
        @NotNull(message = "저자 ID는 필수 입력 값입니다.")
        Long authorId) {
}
