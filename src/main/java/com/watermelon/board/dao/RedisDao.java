package com.watermelon.board.dao;

import com.watermelon.board.common.utils.JsonUtils;
import com.watermelon.board.common.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

import static com.watermelon.board.common.constants.WMBConstants.BOARDID;

/**
 * redis中存储
 */
@Component
public class RedisDao {

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private JsonUtils jsonUtils;

    /**
     * todo 存储一个笔触数据
     *
     * @return 根据版本判断
     * true 添加成功
     * false 添加失败
     */
    public boolean addDraw() {

        return false;
    }



    /**
     * 获取所有笔触数据
     * todo 存在并发问题，需要修改
//     * 1. sheetId如果不是全局唯一，需要改动 解决
     * 2. 非原子操作，可能出问题
     */
    public String getDrawOfBoard(Long boardId) {
        String boardKey = redisUtils.getBoardKey(boardId);
        String sheetListKey = (String) redisUtils.getHashValue(boardKey, String.valueOf(boardId));
        List<String> sheetIds = redisUtils.getListValue(sheetListKey);
        HashMap<String, Object> drawMap = new HashMap<>();
        for (String sheetId : sheetIds) {
            Object draws = redisUtils.getListValue(redisUtils.getDrawListKey(boardId, Long.valueOf(sheetId)));
            drawMap.put(sheetId, draws);
        }
        return jsonUtils.MapTOJson(drawMap);
    }

    public Long createBoard() {
        // 创建boardID;
        Boolean absent = false;
        Long boardId;
        do {
            boardId = redisUtils.getId(BOARDID, null);
            // 在redis中存放
            absent = redisUtils.setHashId(redisUtils.getBoardKey(boardId), String.valueOf(boardId), "");
        } while (!absent);
        return boardId;
    }

    /**
     * 创建新sheet
     *
     * @param boardId
     * @return
     */
    public Long createSheet(Long boardId) {
        return redisUtils.getId(redisUtils.getSheetKey(boardId), null);
    }

    public boolean deleteSheet(Long boardId, Long sheetId) {
        return redisUtils.setHashId(redisUtils.getBoardKey(boardId), redisUtils.getSheetKey(sheetId), null);
    }
}
