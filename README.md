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

### 소개  
RateLimit에서 지원하는 파라미터는 아래와 같습니다.  
파라미터를 조합해서 처리율을 간편하게 조작할 수 있습니다.  

- limit(요청 제한 수)(기본 값 = 1)
- duration(버킷 토큰 리필 시간)
- timeUnit(버킷 토큰 리필 시간 단위)
  - SECOND(초)(기본 값)
  - MINUTE(분)
  - HOUR(시)
  - DAY(일)
- deliveryType(전송 매개체)
  - HTTP_HEADER(헤더)(기본 값)
  - HTTP_BODY(바디)
  - COOKIE(쿠키)
  - QUERY_PARAMTER(쿼리 파라미터)
- keyType(키 타입)
  - GLOBAL(기본 값, 모든 사용자의 요청을 전역 키를 사용해서 처리율을 제한합니다.)
  - IP
  - API_KEY
  - TOKEN
- keyName(키 이름)(기본 값 = "")









[anotazer-ratelimit]: https://img.shields.io/badge/anotazer-ratelimit-blue
