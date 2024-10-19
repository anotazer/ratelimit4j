package io.github.anotazer.ratelimit4j.service.strategy.impl.body;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.anotazer.ratelimit4j.exception.ErrorCode;
import io.github.anotazer.ratelimit4j.exception.custom.RateLimitException;
import io.github.anotazer.ratelimit4j.service.strategy.BodyParseStrategy;

import java.util.Objects;
import java.util.Optional;

public class JsonStrategy implements BodyParseStrategy {
  private final ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public String parse(String body, String keyName) throws IllegalArgumentException {
    JsonNode root = parseJson(body);
    return Optional.ofNullable(Objects.requireNonNull(findKey(root, keyName)).asText())
        .orElseThrow(() -> new RateLimitException.Builder(ErrorCode.BAD_REQUEST).build());
  }

  private JsonNode parseJson(String body) {
    try {
      return objectMapper.readTree(body);
    } catch (Exception e) {
      throw new RateLimitException.Builder(ErrorCode.INTERNAL_SERVER_ERROR).build();
    }
  }

  private JsonNode findKey(JsonNode node, String keyName) {
    if (node.has(keyName)) {
      return node.get(keyName);
    }

    for (JsonNode child : node) {
      if (child.isObject() || child.isArray()) {
        JsonNode result = findKey(child, keyName);
        if (result != null) {
          return result;
        }
      }
    }

    return null;
  }
}
