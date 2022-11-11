package com.watermelon.board.common.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.UUID;

public class redisUtils {

    @Autowired
    private RedisTemplate redisTemplate;

    public Long getId(String key, String hashKey, Long offset) {
        try {
            if (null == offset) {
                offset = 1L;
            }
            // 生成唯一id
            return redisTemplate.opsForHash().increment(key, hashKey, offset);
        } catch (Exception e) {
            //若是出现异常就是用uuid来生成唯一的id值
            int randNo = UUID.randomUUID().toString().hashCode();
            if (randNo < 0) {
                randNo = -randNo;
            }
            return Long.valueOf(String.format("%16d", randNo));
        }
    }
}
