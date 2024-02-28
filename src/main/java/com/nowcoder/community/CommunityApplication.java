package com.nowcoder.community;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CommunityApplication {

    public static void main(String[] args) {
        // 启动tomcat
        // 自动创建spring 容器---自动扫描某些包下的bean---将bean装配到容器里
        SpringApplication.run(CommunityApplication.class, args);
    }

}
