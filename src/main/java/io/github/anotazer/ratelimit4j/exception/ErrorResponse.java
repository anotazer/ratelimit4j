package io.github.anotazer.ratelimit4j.exception;

public class ErrorResponse {

    private final String code;
    private final String message;
    private final Object payload;

    private ErrorResponse(Builder builder) {
        this.code = builder.code;
        this.message = builder.message;
        this.payload = builder.payload;
    }

    // Getter
    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Object getPayload() {
        return payload;
    }

    // Builder 클래스
    public static class Builder {
        private final String code;
        private final String message;
        private Object payload;

        public Builder(ErrorCode errorCode) {
            this.code = errorCode.getCode();
            this.message = errorCode.getMessage();
        }

        public Builder withPayload(Object payload) {
            this.payload = payload;
            return this;
        }

        public ErrorResponse build() {
            return new ErrorResponse(this);
        }
    }
}
