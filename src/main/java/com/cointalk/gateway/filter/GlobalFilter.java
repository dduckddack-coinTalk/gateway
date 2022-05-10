package com.cointalk.gateway.filter;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import reactor.core.publisher.Mono;


@Component
@Slf4j
public class GlobalFilter extends AbstractGatewayFilterFactory<GlobalFilter.Config> {
    public GlobalFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            log.info("Param :"+request.getQueryParams());
            log.info("Header :"+request.getHeaders());
            log.info("URI :"+request.getURI());

            // 토큰필터 (나중에 구현 필수)

            return chain.filter(exchange).then(Mono.fromRunnable(()->{log.info("GlobalFilter End");}));
        });
    }


    @Data
    //application.yml에 선언한 각 filter의 args 사용을 위한 클래스
    public static class Config {
    }
}
