package com.wp.anybox;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.wp.anybox")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
