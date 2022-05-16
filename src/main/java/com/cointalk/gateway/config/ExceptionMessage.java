package com.cointalk.gateway.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ExceptionMessage {

    MissingToken("Header 안에 토큰이 존재하지 않습니다.", HttpStatus.UNAUTHORIZED),
    ExpiredToken("만료된 토큰입니다.", HttpStatus.UNAUTHORIZED),
    InvalidToken("유효하지 않은 토큰입니다.", HttpStatus.UNAUTHORIZED),
    SignatureNotAvailableToken("토큰의 서명이 유효하지 않습니다", HttpStatus.UNAUTHORIZED),
    MalformedToken("토큰이 올바르게 구성되지 않았습니다.", HttpStatus.UNAUTHORIZED),
    UnsupportedToken("지원하지 않는 형식의 토큰입니다.", HttpStatus.UNAUTHORIZED),
    UnknownException("알 수 없는 에러 발생", HttpStatus.BAD_REQUEST)
    ;

    private String message;
    private HttpStatus httpStatus;
}
