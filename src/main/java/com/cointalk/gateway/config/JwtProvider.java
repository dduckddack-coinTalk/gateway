package com.cointalk.gateway.config;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;

@Component
public class JwtProvider {

    @Value("${jwt.secret}")
    private String secret;

    private final ExceptionHandler exceptionHandler;

    public JwtProvider(ExceptionHandler exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
    }

    public Mono<Void> validateToken(ServerHttpResponse response, String token){
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));

        try{
            // 헤더에 토큰없음
            if("".equals(token) || token == null){
                return exceptionHandler.onException(response, ExceptionMessage.MissingToken);
            }
            Jwts.parserBuilder().setSigningKey(key).build().parse(token);
            return Mono.empty();
        }
        // 기간만료된 토큰
        catch (ExpiredJwtException e) {
            return exceptionHandler.onException(response, ExceptionMessage.ExpiredToken);
        }
        // 서명이 유효 하지않은 토큰
        catch (SignatureException e){
            return exceptionHandler.onException(response, ExceptionMessage.SignatureNotAvailableToken);
        }
        // 올바르게 구성되지 않은 토큰
        catch (MalformedJwtException e){
            return exceptionHandler.onException(response, ExceptionMessage.MalformedToken);
        }
        // 예상하는 형식과 일치하지 않는 특정 형식이나 구성의 토큰
        catch (UnsupportedJwtException e){
            return exceptionHandler.onException(response, ExceptionMessage.UnsupportedToken);
        }
        // 기타에러
        catch (Exception e){
            return exceptionHandler.onException(response, ExceptionMessage.UnknownException);
        }
    }

}
