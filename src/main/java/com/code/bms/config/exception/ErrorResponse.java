package com.code.bms.config.exception;

public record ErrorResponse(ErrorCode errorCode, String message, Object payload) {
}