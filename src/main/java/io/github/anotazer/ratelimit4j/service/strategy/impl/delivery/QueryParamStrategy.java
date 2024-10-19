package io.github.anotazer.ratelimit4j.service.strategy.impl.delivery;

import io.github.anotazer.ratelimit4j.annotation.RateLimit;
import io.github.anotazer.ratelimit4j.exception.ErrorCode;
import io.github.anotazer.ratelimit4j.exception.custom.RateLimitException;
import io.github.anotazer.ratelimit4j.service.strategy.DeliveryStrategy;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

public class QueryParamStrategy implements DeliveryStrategy {
    @Override
    public String getKey(HttpServletRequest request, RateLimit rateLimit) {

        return Optional.ofNullable(request.getParameter(rateLimit.keyName()))
                .filter(key -> !key.isBlank())
                .orElseThrow(() -> new RateLimitException.Builder(ErrorCode.KEY_NAME_NOT_FOUND)
                        .withPayload("Requested key name: " + rateLimit.keyName())
                        .build());
    }
}
