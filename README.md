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

### 파라미터  
RateLimit에서 지원하는 파라미터는 아래와 같습니다.  
파라미터를 조합해서 처리율을 간편하게 조작할 수 있습니다.  
```yml
limit(요청 제한 수): <default: 1>
duration(버킷 토큰 리필 시간): <required>
timeUnit(버킷 토큰 리필 시간 단위): <default: SECOND>
  SECOND
  MINUTE
  HOUR
  DAY
deliveryType(전송 매개체): <default: HTTP_HEADER>
  HTTP_HEADER
  HTTP_BODY
  COOKIE
  QUERY_PARAMTER
keyType(키 타입): <default: GLOBAL>
  GLOBAL(모든 사용자의 요청을 전역 키를 사용해서 처리율을 제한합니다.)
  IP
  API_KEY
  TOKEN
keyName(키 이름): <default: "">
```

### 사용 예시
1. SNS 메시지 처리율 제한. (IP와 HTTP_HEADER를 곁들인)
```java
@RestController
@ReqeustMapping("/api/v1/chat")
public class ChatController {

    // IP별, 1초에 3번의 채팅만 허용
    @PostMapping("/send")
    @RateLimit(limit = 3,
        duration = 1,
        timeUnit = TimeUnit.SECOND,
        deliveryType = DeliveryType.HTTP_HEADER,
        keyType = KeyType.IP)
    public String sendMessage(@ReuqestBody RequestDTO request) {
        // 채팅 로직
        return "전송완료";
    }
}
```

2. 무엇이든 대답해주는 생성형 AI 서비스. (API_KEY와 HTTP_HEADER를 곁들인)
```java
@RestController
@ReqeustMapping("/api/v1/gpt")
public class GptController {

    // API_KEY별, 1분에 3번의 요청만 허용
    @GetMapping
    @RateLimit(limit = 3,
        duration = 1,
        timeUnit = TimeUnit.MINUTE,
        deliveryType = DeliveryType.HTTP_HEADER,
        keyType = KeyType.API_KEY,
        keyName = gptKey)
    public String makeResponse(@ReuqestBody RequestDTO request) {
        // 대답 생성 로직
        return "생성된 대답";
    }
}
```
3. 모든 요청을 공평하게 1초에 1번으로 제한 하겠어 ! (Health체크는 제외 하고)
```java
@RateLimit(duration = 1)
@RestController
@RequestMapping("/api/v1")
public class AllLimitController {

    @NoRateLimit
    @GetMapping("/health")
    public String health() {
        return "ok";
    }

    @GetMapping("/doSomething1")
    public String doSomething1() {
        return "something1";
    }

    @GetMapping("/doSomething2")
    public String doSomething2() {
        return "something2";
    }
}
```




[anotazer-ratelimit]: https://img.shields.io/badge/anotazer-ratelimit-blue
