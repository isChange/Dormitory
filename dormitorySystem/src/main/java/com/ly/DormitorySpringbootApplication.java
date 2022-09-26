package com.ly;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@MapperScan("com.ly.mapper")
@SpringBootApplication
public class DormitorySpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(DormitorySpringbootApplication.class, args);
    }

}
