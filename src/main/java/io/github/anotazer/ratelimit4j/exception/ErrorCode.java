package io.github.anotazer.ratelimit4j.exception;

public enum ErrorCode {

    // 코드와 메시지를 포함한 에러코드를 정의한다.
    BAD_REQUEST("400", "Bad Request"),
    TOO_MANY_REQUESTS("429", "Too many requests"),

    KEY_NAME_NOT_FOUND("KEY_001", "The specified key name does not exist"),
    COOKIE_NOT_FOUND("COOKIE_001", "The specified cookie does not exist"),
    INTERNAL_SERVER_ERROR("500", "Internal server error");

    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    // Getter
    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
