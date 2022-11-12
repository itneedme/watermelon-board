package com.watermelon.board.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class ServerBootStrap {

    @Autowired
    public RedisTemplate redisTemplate;

    /**
     * 服务器初始化
     */
    public void initServer() {

    }

    /**
     * redis初始化
     */
    public void initRedis() {
        // 唯一id

    }

}
