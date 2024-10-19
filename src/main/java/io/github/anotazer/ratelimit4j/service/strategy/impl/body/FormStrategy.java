package io.github.anotazer.ratelimit4j.service.strategy.impl.body;

import io.github.anotazer.ratelimit4j.service.strategy.BodyParseStrategy;

public class FormStrategy implements BodyParseStrategy {
  @Override
  public String parse(String body, String keyName) throws IllegalArgumentException {
    // Todo form parse
    return null;
  }
}
