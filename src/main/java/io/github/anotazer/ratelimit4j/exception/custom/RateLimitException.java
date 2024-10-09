package io.github.anotazer.ratelimit4j.exception.custom;

import io.github.anotazer.ratelimit4j.exception.ErrorCode;

public class RateLimitException extends RuntimeException {
    private final ErrorCode errorCode;
    private final Object payload;

    public RateLimitException(Builder builder) {
        this.errorCode = builder.errorCode;
        this.payload = builder.payload;
    }

    // Getter
    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public Object getPayload() {
        return payload;
    }

    // Builder 클래스
    public static class Builder {
        private final ErrorCode errorCode;
        private Object payload;

        public Builder(ErrorCode errorCode) {
            this.errorCode = errorCode;
        }

        public Builder withPayload(Object payload) {
            this.payload = payload;
            return this;
        }

        public RateLimitException build() {
            return new RateLimitException(this);
        }
    }
}
