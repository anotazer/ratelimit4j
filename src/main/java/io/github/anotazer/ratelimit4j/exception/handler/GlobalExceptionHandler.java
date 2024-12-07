package io.github.anotazer.ratelimit4j.exception.handler;

import io.github.anotazer.ratelimit4j.exception.ErrorCode;
import io.github.anotazer.ratelimit4j.exception.ErrorResponse;
import io.github.anotazer.ratelimit4j.exception.custom.RateLimitException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RateLimitException.class)
    public ResponseEntity<ErrorResponse> handleRateLimitException(RateLimitException e) {
        ErrorResponse errorResponse = new ErrorResponse.Builder(e.getErrorCode())
            .withPayload(e.getPayload())
            .build();

        HttpStatus httpStatus = HttpStatus.TOO_MANY_REQUESTS;
        return ResponseEntity.status(httpStatus).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception e) {
        ErrorResponse errorResponse = new ErrorResponse.Builder(ErrorCode.INTERNAL_SERVER_ERROR)
            .withPayload(e.getMessage())
            .build();

        HttpStatusCode httpStatusCode = HttpStatus.INTERNAL_SERVER_ERROR;
        return ResponseEntity.status(httpStatusCode).body(errorResponse);
    }

}
