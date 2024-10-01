package io.github.anotazer.ratelimit4j.service.strategy.impl;

import io.github.anotazer.ratelimit4j.annotation.RateLimit;
import io.github.anotazer.ratelimit4j.service.strategy.DeliveryStrategy;
import jakarta.servlet.http.HttpServletRequest;

public class QueryParamStrategy implements DeliveryStrategy {
  @Override
  public String getKey(HttpServletRequest request, RateLimit rateLimit) {
    /*
    KeyName 존재 -> 값 리턴
    KeyName 없음 -> 예외
     */
    return "query";
  }
}
