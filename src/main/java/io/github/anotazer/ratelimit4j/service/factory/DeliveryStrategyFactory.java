package io.github.anotazer.ratelimit4j.service.factory;

import io.github.anotazer.ratelimit4j.enums.DeliveryType;
import io.github.anotazer.ratelimit4j.service.strategy.DeliveryStrategy;
import io.github.anotazer.ratelimit4j.service.strategy.impl.delivery.BodyStrategy;
import io.github.anotazer.ratelimit4j.service.strategy.impl.delivery.CookieStrategy;
import io.github.anotazer.ratelimit4j.service.strategy.impl.delivery.HeaderStrategy;
import io.github.anotazer.ratelimit4j.service.strategy.impl.delivery.QueryParamStrategy;

public class DeliveryStrategyFactory {
  public static DeliveryStrategy getStrategy(DeliveryType type) {
    return switch (type) {
      case HTTP_HEADER -> new HeaderStrategy();
      case HTTP_BODY -> new BodyStrategy();
      case COOKIE -> new CookieStrategy();
      case QUERY_PARAMETER -> new QueryParamStrategy();
    };
  }
}
