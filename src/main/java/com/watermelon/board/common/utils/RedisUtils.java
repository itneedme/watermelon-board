package com.watermelon.board.common.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

import static com.watermelon.board.common.constants.WMBConstants.*;

@Component
@Scope("singleton")
public class RedisUtils {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public Long getHashId(String key, String hashKey, Long offset) {
        try {
            offset = offset == null ? 1L : offset;
            return redisTemplate.opsForHash().increment(key, hashKey, offset);
        } catch (Exception e) {
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

    public Boolean setHashId(String key, String hashKey, String value) {
        return redisTemplate.opsForHash().putIfAbsent(key, hashKey, value);
    }

    private Long randomUUID() {
        int randNo = UUID.randomUUID().toString().hashCode();
        if (randNo < 0) {
            randNo = -randNo;
        }
        return Long.valueOf(String.format("%16d", randNo));
    }

    public String getBoardKey(Long boardId) {
        return PREFIX_BOARD + boardId;
    }

    public String getSheetKey(Long boardId) {
        return PREFIX_SHEET + boardId;
    }

    public String getDrawListKey(Long boardId, Long sheetId) {
        return boardId + ":" + sheetId;
    }

    public String getUndoKey(String boardId, String sheetId) {
        return PREFIX_UNDO + boardId + sheetId;
    }

    public List<String> getListValue(String key) {
        return redisTemplate.opsForList().range(key, 0, -1);
    }

    public Object getHashValue(String key, String hashKey) {
        return redisTemplate.opsForHash().get(key, hashKey);
    }

    public Long appendToList(String key, String value) {
        return redisTemplate.opsForList().rightPush(key, value);
    }

    public String getValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public Long listRemove(String key, String value) {
        return redisTemplate.opsForList().remove(key, 1, value);
    }

    public Boolean delete(String key) {
        return redisTemplate.delete(key);
    }

    public Long listAdd(String key, String value) {
        return redisTemplate.opsForList().rightPush(key, value);
    }

    public boolean contains(String key) {
        return redisTemplate.hasKey(key);
    }
}
