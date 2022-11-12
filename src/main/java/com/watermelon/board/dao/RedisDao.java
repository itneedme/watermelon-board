package com.watermelon.board.dao;

import com.watermelon.board.common.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.watermelon.board.common.constants.WMBConstants.BOARDID;

/**
 * redis中存储
 */
@Component
public class RedisDao {

    @Autowired
    private RedisUtils redisUtils;
    // todo 存储一个笔触数据

    // todo 获取所有笔触数据

    public Long createBoard() {
        return redisUtils.getId(BOARDID, null);
    }
}
