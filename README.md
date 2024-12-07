# Rate Limit ![anotazer][anotazer-ratelimit]

```@RateLimit```을 붙여서 손쉽게 API 처리율을 제한 할 수 있습니다.  
기존에 존재하는 RateLimit을 경량화 하고, 버킷 알고리즘을 이용하여 처리율 제한을 구현했습니다.  

### 시작하기
1. 의존성을 추가하세요.
```xml
<dependency>
    <groupId>io.github.anotazer</groupId>
    <artifactId>ratelimit4j</artifactId>
    <version>1.0.0</version>
</dependency>
```
2. @RateLimit 어노테이션을 추가하세요.
```java
// 어노테이션에 파라미터를 설정해서 원하는 형태로 처리율 제한이 가능합니다.
@RateLimit(
  limit = 1,
  duration = 1,
  timeUnit = TimeUnit.MINUTE,
  deliveryType = DeliveryType.HTTP_HEADER,
  keyType = KeyType.IP
) 
```

[anotazer-ratelimit]: https://img.shields.io/badge/anotazer-ratelimit-blue
