package com.code.bms.config.exception;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.logging.LogLevel;

@Getter
@RequiredArgsConstructor
public enum ErrorType {
    AUTHOR_NOT_FOUND(ErrorCode.NOT_FOUND, "저자를 찾을 수 없습니다.", LogLevel.WARN)
    , BOOK_NOT_FOUND(ErrorCode.NOT_FOUND, "도서를 찾을 수 없습니다.", LogLevel.WARN)

    , EMAIL_ALREADY_EXISTS(ErrorCode.CONFLICT, "이미 존재하는 이메일 입니다.", LogLevel.WARN)
    , ISBN_ALREADY_EXISTS(ErrorCode.CONFLICT, "이미 존재하는 ISBN 입니다.", LogLevel.WARN)
    , INVALID_ISBN_FORMAT(ErrorCode.BAD_REQUEST, "잘못된 ISBN-10 형식입니다.", LogLevel.WARN)

    , AUTHOR_DELETE_CONFLICT(ErrorCode.CONFLICT, "해당 저자와 연관된 도서가 있어 삭제할 수 없습니다.", LogLevel.WARN)
    ;

    private final ErrorCode errorCode;
    private final String message;
    private final LogLevel logLevel;
}