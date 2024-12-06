package com.xyt.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author : ytxu5
 * @Date : Created in 15:40 2024/11/22
 * @Description:
 */
@Configuration
public class TestConfig {

    @Bean
    @ConfigurationProperties(prefix = "test.config")
    public TestProperties testProperties(){
        return new TestProperties();
    }
}
