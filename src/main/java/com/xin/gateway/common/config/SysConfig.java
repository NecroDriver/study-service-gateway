package com.xin.gateway.common.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * SysConfig 系统配置项
 *
 * @author mfh 2020/6/28 16:10
 * @version 1.0.0
 **/
@Configuration
@Getter
@RefreshScope
public class SysConfig {

    /**
     * 请求白名单
     */
    @Value("${system.properties.whiteList}")
    private String whiteList;
}
