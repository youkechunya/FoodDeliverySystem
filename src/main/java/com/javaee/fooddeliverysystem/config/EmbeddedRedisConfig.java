package com.javaee.fooddeliverysystem.config;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Configuration;
import redis.embedded.RedisServer;

import java.io.IOException;

/**
 * 项目启动时自动拉起内嵌 Redis（监听 6379），关闭时自动停止。
 * 本地开发无需手动安装/启动 Redis。
 */
@Configuration
public class EmbeddedRedisConfig {

    private RedisServer redisServer;

    @PostConstruct
    public void start() throws IOException {
        redisServer = RedisServer.newRedisServer()
                .port(6379)
                .setting("maxmemory 64M")
                .build();
        redisServer.start();
        System.out.println(">>> Embedded Redis started on port 6379");
    }

    @PreDestroy
    public void stop() throws IOException {
        if (redisServer != null && redisServer.isActive()) {
            redisServer.stop();
            System.out.println(">>> Embedded Redis stopped");
        }
    }
}