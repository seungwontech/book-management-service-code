package com.code.bms.config;


import com.code.bms.config.exception.CoreException;
import com.code.bms.config.exception.ErrorCode;
import com.code.bms.config.exception.ErrorResponse;
import com.code.bms.config.exception.ErrorType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.logging.LogLevel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    /**
     * CoreException을 처리하는 메서드
     */
    @ExceptionHandler(CoreException.class)
    public ResponseEntity<ErrorResponse> handleCoreException(CoreException coreException) {
        logError(coreException.getErrorType());

        ErrorResponse errorResponse = new ErrorResponse(coreException.getErrorType().getErrorCode(), coreException.getErrorType().getMessage(), coreException.getPayload());
        HttpStatus status = mapToHttpStatus(coreException.getErrorType().getErrorCode());
        return new ResponseEntity<>(errorResponse, status);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        // 첫 번째 오류 메시지 가져오기
        FieldError fieldError = ex.getBindingResult().getFieldError();
        String errorMessage = (fieldError != null) ? fieldError.getDefaultMessage() : "유효성 검사 오류";

        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.BAD_REQUEST, errorMessage, null);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    /**
     * 그 외의 모든 예외를 처리하는 메서드
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllExceptions(Exception exception) {
        log.warn("Exception occur: ", exception);
        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.DB_ERROR, "서버 내부 오류가 발생했습니다.", null);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 에러 타입에 따라 HTTP 상태 코드 매핑
     */
    private HttpStatus mapToHttpStatus(ErrorCode errorCode) {
        return switch (errorCode) {
            case NOT_FOUND -> HttpStatus.NOT_FOUND;
            case BAD_REQUEST -> HttpStatus.BAD_REQUEST;
            case CONFLICT -> HttpStatus.CONFLICT;
            default -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
    }

    /**
     * 에러 로그 기록
     */
    private void logError(ErrorType errorType) {
        LogLevel logLevel = errorType.getLogLevel();
        switch (logLevel) {
            case WARN -> log.warn(errorType.getMessage());
            case ERROR -> log.error(errorType.getMessage());
            default -> log.info(errorType.getMessage());
        }
    }
}