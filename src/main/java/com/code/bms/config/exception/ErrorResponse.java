package com.code.bms.config.exception;

import io.swagger.v3.oas.annotations.media.Schema;

public record ErrorResponse(
        @Schema(description = "에러 코드", example = "BAD_REQUEST")
        ErrorCode errorCode,

        @Schema(description = "에러 메시지", example = "제목은 필수 입력 값입니다.")
        String message,

        @Schema(description = "추가 데이터", nullable = true , example = "pk,fk,value or null")
        Object payload
) {
}