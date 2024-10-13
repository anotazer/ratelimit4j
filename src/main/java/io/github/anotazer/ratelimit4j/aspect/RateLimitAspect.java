package io.github.anotazer.ratelimit4j.aspect;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import io.github.anotazer.ratelimit4j.annotation.RateLimit;
import io.github.anotazer.ratelimit4j.exception.ErrorCode;
import io.github.anotazer.ratelimit4j.exception.custom.RateLimitException;
import io.github.anotazer.ratelimit4j.service.RateLimitService;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Aspect
@Component
public class RateLimitAspect {
  @Autowired
  private HttpServletRequest httpServletRequest;
  private final RateLimitService rateLimitService;

  private final Cache<String, Bucket> buckets = Caffeine.newBuilder()
    .maximumSize(10000)
    .expireAfterAccess(30, TimeUnit.MINUTES)
    .build();

  private RateLimitAspect(RateLimitService rateLimitService) {
    this.rateLimitService = rateLimitService;
  }

  // @RateLimit 어노테이션이 적용된 메서드 및 클래스에 대한 포인트컷 정의
  @Pointcut("@within(io.github.anotazer.ratelimit4j.annotation.RateLimit) || @annotation(io.github.anotazer.ratelimit4j.annotation.RateLimit)")
  public void rateLimitMethods() {}

  // @NoRateLimit 어노테이션이 없는 메서드에 대한 포인트컷 정의
  @Pointcut("execution(* *(..)) && !@annotation(io.github.anotazer.ratelimit4j.annotation.NoRateLimit)")
  public void nonNoRateLimitMethods() {}

  @Around("rateLimitMethods() && nonNoRateLimitMethods()")
  public Object applyRateLimit(ProceedingJoinPoint joinPoint) throws Throwable {
    RateLimit rateLimit = getRateLimitAnnotation(joinPoint);

    if (rateLimit == null) {
      return joinPoint.proceed();
    }

    String key = getKey(rateLimit);
    Bucket bucket = buckets.get(key, k -> createBucket(rateLimit));

    if (bucket.tryConsume(1)) {
      return joinPoint.proceed();
    } else {
      throw new RateLimitException.Builder(ErrorCode.TOO_MANY_REQUESTS).build();
    }
  }

  // 호출되는 메서드의 RateLimit 정보 가져오기
  private RateLimit getRateLimitAnnotation(ProceedingJoinPoint joinPoint) throws NoSuchMethodException {
    Method method = joinPoint.getTarget().getClass()
        .getMethod(joinPoint.getSignature().getName(), ((MethodSignature) joinPoint.getSignature()).getParameterTypes());

    // 우선 메서드에 붙은 어노테이션 체크
    RateLimit rateLimit = AnnotationUtils.findAnnotation(method, RateLimit.class);

    // 메서드에 없다면 클래스에 붙은 어노테이션 체크
    if (rateLimit == null) {
      rateLimit = AnnotationUtils.findAnnotation(joinPoint.getTarget().getClass(), RateLimit.class);
    }

    return rateLimit;
  }

  private String getKey(RateLimit rateLimit) {
    return rateLimitService.extractKey(httpServletRequest, rateLimit);
  }

  private Bucket createBucket(RateLimit rateLimit) {
    Duration duration = Duration.ofSeconds(rateLimit.timeUnit().toSeconds(rateLimit.duration()));
    Bandwidth limit = Bandwidth.classic(rateLimit.limit(), Refill.intervally(rateLimit.limit(), duration));
    return Bucket.builder().addLimit(limit).build();
  }
}
