package io.github.anotazer.ratelimit4j.service.strategy.impl.body;

import io.github.anotazer.ratelimit4j.exception.ErrorCode;
import io.github.anotazer.ratelimit4j.exception.custom.RateLimitException;
import io.github.anotazer.ratelimit4j.service.strategy.BodyParseStrategy;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class FormStrategy implements BodyParseStrategy {
  @Override
  public String parse(String body, String keyName) throws IllegalArgumentException {
    return Arrays.stream(body.split("&"))
        .map(pair -> pair.split("=", 2))
        .filter(keyValue -> keyValue.length == 2 && keyValue[0].equals(keyName))
        .map(keyValue -> decodeURIComponent(keyValue[1]))
        .findFirst()
        .orElseThrow(() -> new RateLimitException.Builder(ErrorCode.KEY_NAME_NOT_FOUND)
            .withPayload("Requested key name: " + keyName)
            .build());
  }

  private String decodeURIComponent(String encoded) {
    return URLDecoder.decode(encoded, StandardCharsets.UTF_8);
  }
}
