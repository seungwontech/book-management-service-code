package com.code.bms.config.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    NOT_FOUND("404: 리소스를 찾을 수 없음"),
    DB_ERROR("500: 데이터베이스 오류 발생"),
    BAD_REQUEST("400: 클라이언트 요청 오류"),
    CONFLICT("409: 요청 충돌 발생");

    private final String desc;

    ErrorCode(String desc) {
        this.desc = desc;
    }

}
