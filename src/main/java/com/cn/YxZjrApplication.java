package com.cn;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@tk.mybatis.spring.annotation.MapperScan("com.cn.dao")
@org.mybatis.spring.annotation.MapperScan("com.cn.dao")
public class YxZjrApplication {

    public static void main(String[] args) {
        SpringApplication.run(YxZjrApplication.class, args);
    }

}
