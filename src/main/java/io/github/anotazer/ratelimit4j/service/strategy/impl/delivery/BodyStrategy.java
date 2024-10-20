package io.github.anotazer.ratelimit4j.service.strategy.impl.delivery;

import io.github.anotazer.ratelimit4j.annotation.RateLimit;
import io.github.anotazer.ratelimit4j.exception.ErrorCode;
import io.github.anotazer.ratelimit4j.exception.custom.RateLimitException;
import io.github.anotazer.ratelimit4j.service.factory.BodyParseStrategyFactory;
import io.github.anotazer.ratelimit4j.service.strategy.BodyParseStrategy;
import io.github.anotazer.ratelimit4j.service.strategy.DeliveryStrategy;
import jakarta.servlet.http.HttpServletRequest;

import java.io.BufferedReader;
import java.io.IOException;

public class BodyStrategy implements DeliveryStrategy {
  @Override
  public String getKey(HttpServletRequest request, RateLimit rateLimit) {
    String keyName = rateLimit.keyName();
    String body = extractRequestBody(request);
    String contentType = request.getContentType();

    if (body.isEmpty() || contentType == null) {
      throw new RateLimitException.Builder(ErrorCode.BAD_REQUEST).build();
    }

    BodyParseStrategy bodyParseStrategy = BodyParseStrategyFactory.getStrategy(contentType);
    return bodyParseStrategy.parse(body, keyName);
  }

  private String extractRequestBody(HttpServletRequest request) {
    try {
      return getRequestBody(request);
    } catch (Exception e) {
      throw new RateLimitException.Builder(ErrorCode.INTERNAL_SERVER_ERROR).build();
    }
  }

  private String getRequestBody(HttpServletRequest request) throws IOException {
    StringBuilder stringBuilder = new StringBuilder();
    BufferedReader bufferedReader = request.getReader();
    String line;
    while ((line = bufferedReader.readLine()) != null) {
      stringBuilder.append(line);
    }
    return stringBuilder.toString();
  }
}
