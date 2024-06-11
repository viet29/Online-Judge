package com.service.submittask_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SubmittaskServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SubmittaskServiceApplication.class, args);
    }

}
