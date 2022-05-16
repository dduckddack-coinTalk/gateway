package com.cointalk.gateway.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import com.google.gson.JsonObject;


@Component
@Slf4j
public class ExceptionHandler {

    @Value("${jwt.secret}")
    private String secret;

    /*
        에러 발생시 협의된 에러 형식으로 response 전달
     */
//    public Mono<Void> onException(ServerHttpResponse response, ExceptionMessage exceptionMessage) {
//        // response 설정 (HttpStatus, CONTENT_TYPE)
//        response.setStatusCode(exceptionMessage.getHttpStatus());
//        response.getHeaders().set(HttpHeaders.CONTENT_TYPE, String.valueOf(MediaType.APPLICATION_JSON));
//
//        // 에러 메시지 형식 설정
//        JsonObject jsonObject = new JsonObject();
//        jsonObject.addProperty("status", "error");
//        jsonObject.addProperty("message", exceptionMessage.getMessage());
//
//        // jsonByteArray -> Mono<DataBuffer> 로 return
//        byte[] jsonByteArray = jsonObject.toString().getBytes(StandardCharsets.UTF_8);
//
//        DataBuffer buffer = response.bufferFactory().wrap(jsonByteArray);
//        return response.writeWith(Mono.just(buffer));
//    }

    public Mono<Void> onException(ServerHttpResponse response, ExceptionMessage exceptionMessage) {

        // response 설정 (HttpStatus, CONTENT_TYPE)
        response.setStatusCode(exceptionMessage.getHttpStatus());
        response.getHeaders().set(HttpHeaders.CONTENT_TYPE, String.valueOf(MediaType.APPLICATION_JSON));

        // 에러 메시지 형식 설정
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("status", "error");
        jsonObject.addProperty("message", exceptionMessage.getMessage());

        // jsonByteArray -> Mono<DataBuffer> 로 return
        byte[] jsonByteArray = jsonObject.toString().getBytes(StandardCharsets.UTF_8);

        DataBuffer buffer = response.bufferFactory().wrap(jsonByteArray);
        return response.writeWith(Mono.just(buffer));

    }

}
