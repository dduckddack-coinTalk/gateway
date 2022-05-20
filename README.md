# Gateway
코인톡 게이트웨이 서버.
### 개요
코인톡 각 서버간의 라우팅 기능을 제공하고 회원 JWT토큰을 검증하는 서버입니다.
* 클라이언트의 요청을 알맞는 API서버에 라우팅
* JWT토큰 검증

### HowTo Setting


#### SSL 설정
1. 도메인 생성사이트에서 도메인 생성
2. SSL 인증사이트에서 키 파일 생성 
3. application.yml에 해당 정보입력
<li><a href="https://docs.google.com/document/d/1DSh2ryiDK4dcjHXDzTeHhQnvRj3HN2NZiw4jsY4n90c/edit#">SSL 키 파일 등록 위키 참고</a></li>

#### Spring Clound Gateway 설정
1. cors설정
- corsConfigurations
- allowedOrigins
- allowedHeaders
- allowedMethods

2. 라우팅할 서버들 설정 추가
- id : 라우팅할 서버 이름 설정
- uri : 라우팅할 주소 설정 ex) http://localhost:8080
- predicates : uri나 메소드등 
- filters : 해당 서버를 점증할 필터 등록

3. JWT 토큰 파싱할 키값 설정


```yml
server:
  port: 8080
  ssl:
    enabled: true
    key-store: 
    key-store-type: 
    key-password: 
    trust-store: 
    trust-store-password: 
    key-alias: 

spring:
  cloud:
    gateway:
      globalcors:
        corsConfigurations:
          '[CROS 허용할 URL 입력]':
            allowedOrigins: 공유를 허락할 Origin, Headers, Methods 입력
            allowedHeaders: 
            allowedMethods: 
      httpclient: # 타임아웃 설정
        connect-timeout: 3000
        response-timeout: 3s
      routes:
        - id: User Domain Sign Up, Login  # 유저 로그인, 회원가입은 JWT 토큰 인증과정 없음.
          uri: 
          predicates:
            - Method=POST
            - Path=/user/login,/user/account
          filters:
            - GlobalFilter

        - id: User Account Domain
          uri: 
          predicates:
            - Method=PUT
            - Path=/user/account
          filters:
            - JwtAuthenticationFilter

        - id: User Email Auth Domain
          uri: 
          predicates:
            - Path=/user/email/**
          filters:
            - GlobalFilter

        - id: Chatting Domain
          uri: 
          predicates:
            - Path=/chatting/**
          filters:
            - GlobalFilter

        - id: News Domain
          uri: 
          predicates:
            - Path=/news/**
          filters:
            - GlobalFilter

        - id: Data Lake Domain
          uri: 
          predicates:
            - Path=/dataLake/**
          filters:
            - GlobalFilter

        - id: Drawing Image Domain
          uri: 
          predicates:
            - Path=/drawing/**
          filters:
            - JwtAuthenticationFilter

jwt:
  secret: JWT 토큰 파싱할 키값 설정

```



