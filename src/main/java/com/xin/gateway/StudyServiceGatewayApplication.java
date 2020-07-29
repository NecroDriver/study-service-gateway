package com.xin.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author lemon
 */
@SpringBootApplication
@EnableDiscoveryClient
public class StudyServiceGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudyServiceGatewayApplication.class, args);
    }
}
