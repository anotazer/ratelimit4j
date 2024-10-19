package io.github.anotazer.ratelimit4j.service.strategy.impl.delivery;

import io.github.anotazer.ratelimit4j.annotation.RateLimit;
import io.github.anotazer.ratelimit4j.service.strategy.DeliveryStrategy;
import jakarta.servlet.http.HttpServletRequest;

public class CookieStrategy implements DeliveryStrategy {
  @Override
  public String getKey(HttpServletRequest httpServletRequest, RateLimit rateLimit) {
    /*
    쿠키이름 존재 -> 쿠키
    쿠키이름 없음 -> 예외

    KeyName 존재 -> 값 리턴
    KeyName 없음 -> 예외
     */
    return "cookie";
  }
}
