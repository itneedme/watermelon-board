package com.watermelon.board.common.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RedisUtils {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public Long getHashId(String key, String hashKey, Long offset) {
        try {
            offset = offset == null ? 1L : offset;
            return redisTemplate.opsForHash().increment(key, hashKey, offset);
        } catch (Exception e) {
            //若是出现异常就是用uuid来生成唯一的id值
            return randomUUID();
        }
    }

    public Long getId(String key, Long offset) {
        try {
            offset = offset == null ? 1L : offset;
            return redisTemplate.opsForValue().increment(key, offset);
        } catch (Exception e) {
            return randomUUID();
        }
    }

    public void setId(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public void setHashId(String key, String hashKey, Object value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    private Long randomUUID() {
        int randNo = UUID.randomUUID().toString().hashCode();
        if (randNo < 0) {
            randNo = -randNo;
        }
        return Long.valueOf(String.format("%16d", randNo));
    }
}
