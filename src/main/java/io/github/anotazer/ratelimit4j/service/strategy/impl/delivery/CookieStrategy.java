package io.github.anotazer.ratelimit4j.service.strategy.impl.delivery;

import io.github.anotazer.ratelimit4j.annotation.RateLimit;
import io.github.anotazer.ratelimit4j.exception.ErrorCode;
import io.github.anotazer.ratelimit4j.exception.custom.RateLimitException;
import io.github.anotazer.ratelimit4j.service.strategy.DeliveryStrategy;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Arrays;
import java.util.Optional;

public class CookieStrategy implements DeliveryStrategy {
    @Override
    public String getKey(HttpServletRequest request, RateLimit rateLimit) {

        String keyName = Optional.ofNullable(rateLimit.keyName())
                .filter(key -> !key.isBlank())
                .orElseThrow(() -> new RateLimitException.Builder(ErrorCode.KEY_NAME_NOT_FOUND)
                        .withPayload("Requested key name: " + rateLimit.keyName())
                        .build());

        Arrays.stream(Optional.ofNullable(request.getCookies()).orElse(new Cookie[0]))
                .filter(cookie -> cookie.getName().equals(keyName))
                .map(Cookie::getValue)
                .findFirst()
                .orElseThrow(() -> new RateLimitException.Builder(ErrorCode.COOKIE_NOT_FOUND)
                        .withPayload("Requested cookie: " + keyName)
                        .build());

        return keyName;
    }
}
