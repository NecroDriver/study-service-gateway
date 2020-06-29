package com.xin.gateway.filter;

import com.xin.gateway.common.config.SysConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * AuthFilter 权限验证过滤器
 *
 * @author mfh 2020/6/28 16:03
 * @version 1.0.0
 **/
@Component
public class AuthFilter implements GlobalFilter {

    @Autowired
    private SysConfig sysConfig;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 白名单路径
        Set<String> whiteList = getWhiteList();
        String path = exchange.getRequest().getPath().toString();
        // 免登陆匹配
        boolean flagPublic = Pattern.matches("^/study/[^\\s]*/public/[^\\s]*", path);
        // 白名单接口、免登陆接口放行
        if (flagPublic || whiteList.contains(path)){
            return chain.filter(exchange);
        }
        // 常用接口鉴权
        String token = exchange.getRequest().getHeaders().getFirst("token");
        JwtUtil
        return null;
    }

    /**
     * 获取白名单
     *
     * @return 列表
     */
    private Set<String> getWhiteList() {
        String whiteListStr = sysConfig.getWhiteList();
        if (StringUtils.isEmpty(whiteListStr)) {
            return new HashSet<>();
        }
        String[] arr = whiteListStr.split(",");
        return new HashSet<>(Arrays.asList(arr));
    }
}
