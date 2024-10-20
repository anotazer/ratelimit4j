package io.github.anotazer.ratelimit4j.service.factory;

import io.github.anotazer.ratelimit4j.exception.ErrorCode;
import io.github.anotazer.ratelimit4j.exception.custom.RateLimitException;
import io.github.anotazer.ratelimit4j.service.strategy.BodyParseStrategy;
import io.github.anotazer.ratelimit4j.service.strategy.impl.body.FormStrategy;
import io.github.anotazer.ratelimit4j.service.strategy.impl.body.JsonStrategy;

public class BodyParseStrategyFactory {
  public static BodyParseStrategy getStrategy(String contentType) {
    return switch (contentType) {
      case "application/json" -> new JsonStrategy();
      case "application/x-www-form-urlencoded" -> new FormStrategy();
      default -> throw new RateLimitException.Builder(ErrorCode.UNSUPPORTED).build();
    };
  }
}
