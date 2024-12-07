package io.github.anotazer.ratelimit4j.service.strategy;

import io.github.anotazer.ratelimit4j.annotation.RateLimit;
import jakarta.servlet.http.HttpServletRequest;

public interface DeliveryStrategy {
    String getKey(HttpServletRequest request, RateLimit rateLimit);
}
