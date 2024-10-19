package io.github.anotazer.ratelimit4j.service.strategy;

public interface BodyParseStrategy {
  String parse(String body, String keyName) throws IllegalArgumentException;
}
