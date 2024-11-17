package io.github.anotazer.ratelimit4j.service;

import io.github.anotazer.ratelimit4j.annotation.RateLimit;
import io.github.anotazer.ratelimit4j.enums.KeyType;
import io.github.anotazer.ratelimit4j.service.factory.DeliveryStrategyFactory;
import io.github.anotazer.ratelimit4j.service.strategy.DeliveryStrategy;
import jakarta.servlet.http.HttpServletRequest;

public class RateLimitService {
  public String extractKey(HttpServletRequest request, RateLimit rateLimit) {
    if (rateLimit.keyType() == KeyType.GLOBAL) {
      return "GLOBAL_KEY";
    }

    DeliveryStrategy strategy = DeliveryStrategyFactory.getStrategy(rateLimit.deliveryType());
    return strategy.getKey(request, rateLimit);
  }
}
