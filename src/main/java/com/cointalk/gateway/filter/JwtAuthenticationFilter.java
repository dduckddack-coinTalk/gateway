package com.cointalk.gateway.filter;

import com.cointalk.gateway.config.ExceptionHandler;
import com.cointalk.gateway.config.ExceptionMessage;
import com.cointalk.gateway.config.JwtProvider;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class JwtAuthenticationFilter extends AbstractGatewayFilterFactory<JwtAuthenticationFilter.Config> {
    private final ExceptionHandler exceptionHandler;
    private final JwtProvider jwtProvider;

    public JwtAuthenticationFilter(ExceptionHandler exceptionHandler, JwtProvider jwtProvider) {
        super(JwtAuthenticationFilter.Config.class);
        this.exceptionHandler = exceptionHandler;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {

            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();
            String token = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

            log.info("Param :"+request.getQueryParams());
            log.info("Header :"+request.getHeaders());
            log.info("URI :"+request.getURI());

            // 토큰 정합성 체크
            jwtProvider.validateToken(response, token).subscribe();

            // 토큰에 이상이 없으면 Route
            return chain.filter(exchange).then(Mono.fromRunnable(()->{log.info("JwtAuthenticationFilter End");}));
        });
    }

    @Data
    //application.yml에 선언한 각 filter의 args 사용을 위한 클래스
    public static class Config {
    }
}
