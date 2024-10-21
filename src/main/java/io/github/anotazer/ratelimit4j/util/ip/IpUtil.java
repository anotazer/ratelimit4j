package io.github.anotazer.ratelimit4j.util.ip;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Arrays;
import java.util.List;

public final class IpUtil {
  private IpUtil() {
    throw new UnsupportedOperationException("IpUtil is util class");
  }

  private static final String UNKNOWN = "unknown";

  private static final List<String> IP_HEADER_CANDIDATES = Arrays.asList(
      "X-Forwarded-For",
      "X-Real-IP",
      "Proxy-Client-IP",
      "WL-Proxy-Client-IP",
      "HTTP_CLIENT_IP",
      "HTTP_X_FORWARDED_FOR"
  );

  public static String getClientIp(HttpServletRequest request) {
    return IP_HEADER_CANDIDATES.stream()
        .map(request::getHeader)
        .filter(IpUtil::isValidIp)
        .findFirst()
        .map(ip -> ip.split(",")[0].trim())
        .orElse(request.getRemoteAddr());
  }

  private static boolean isValidIp(String ip) {
    return ip != null && !ip.isBlank() && !UNKNOWN.equalsIgnoreCase(ip);
  }
}
