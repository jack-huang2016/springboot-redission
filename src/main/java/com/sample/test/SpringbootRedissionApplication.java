package com.sample.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties //作用：开启 @ConfigurationProperties注解
public class SpringbootRedissionApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootRedissionApplication.class, args);
    }

}
