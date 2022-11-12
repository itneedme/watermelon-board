package com.watermelon.board.server;

import com.watermelon.board.common.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import static com.watermelon.board.common.constants.WMBConstants.BOARDID;
import static com.watermelon.board.common.constants.WMBConstants.DRAWID;

@Component
public class ServerBootStrap {

    @Autowired
    public RedisUtils redisUtils;

    /**
     * 服务器初始化
     */
    public void initServer() {
        initRedis();

    }

    /**
     * redis初始化
     */
    private void initRedis() {
        // 唯一id
        redisUtils.setId(BOARDID, "0");

        redisUtils.setId(DRAWID, "0");

    }

}
