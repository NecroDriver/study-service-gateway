package com.xin.gateway.common.factory;

import com.alibaba.fastjson.JSONObject;
import com.xin.common.vo.ResultVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractNameValueGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * HeaderGatewayFilterFactory 头部网关过滤器
 *
 * @author mfh 2020/7/28 16:27
 * @version 1.0.0
 **/
@Component
public class HeaderGatewayFilterFactory extends AbstractNameValueGatewayFilterFactory {

    @Override
    public GatewayFilter apply(NameValueConfig config) {
        // filter pre post
        return ((exchange, chain) -> {
            // pre 校验header
            String clientValue = exchange.getRequest().getHeaders().getFirst(config.getName());
            if (StringUtils.isNotEmpty(clientValue) && clientValue.equals(config.getValue())) {
                return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                    // post
                }));
            } else {
                // 异常直接返回
                ServerHttpResponse response = exchange.getResponse();
                byte[] bits = JSONObject.toJSONString(ResultVo.failureVo("header参数错误")).getBytes(StandardCharsets.UTF_8);
                DataBuffer buffer = response.bufferFactory().wrap(bits);
                response.setStatusCode(HttpStatus.OK);
                // 指定编码，否则在浏览器中会中文乱码
                response.getHeaders().add("Content-Type", "text/plain;charset=UTF-8");
                return response.writeWith(Mono.just(buffer));
            }
        });
    }
}
