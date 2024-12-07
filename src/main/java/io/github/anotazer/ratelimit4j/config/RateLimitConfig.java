package io.github.anotazer.ratelimit4j.config;

import io.github.anotazer.ratelimit4j.aspect.RateLimitAspect;
import io.github.anotazer.ratelimit4j.service.RateLimitService;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class RateLimitConfig {
    @Bean
    public RateLimitService rateLimitService() {
        return new RateLimitService();
    }

    @Bean
    public RateLimitAspect rateLimitAspect(RateLimitService rateLimitService) {
        return new RateLimitAspect(rateLimitService);
    }
}
