package io.github.anotazer.ratelimit4j.annotation;

import io.github.anotazer.ratelimit4j.enums.DeliveryType;
import io.github.anotazer.ratelimit4j.enums.KeyType;
import io.github.anotazer.ratelimit4j.enums.TimeUnit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface RateLimit {
    long limit() default 1; // 허용 최대 요청 수

    TimeUnit timeUnit() default TimeUnit.SECOND; // 시간 단위

    int duration(); // 시간 값

    DeliveryType deliveryType() default DeliveryType.HTTP_HEADER; // 전송 매개체

    KeyType keyType() default KeyType.GLOBAL; // 키 타입

    String keyName() default ""; // 식별 키 이름
}
