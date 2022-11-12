package com.watermelon.board.dao;

import com.watermelon.board.common.utils.JsonUtils;
import com.watermelon.board.common.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static com.watermelon.board.common.constants.WMBConstants.BOARDID;
import static com.watermelon.board.common.constants.WMBConstants.DRAWID;

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
     * 添加成功 转发所有人，并返回drawId
     * 添加失败 返回现在的drawId
     */
    public Long addDraw(Long boardId, Long sheetId, String draw, Long _drawId) {
        Long drawId = Long.valueOf(redisUtils.getValue(DRAWID));
        if (!Objects.equals(drawId, _drawId)) {
            return drawId;
        }
        drawId = redisUtils.getId(DRAWID, null);
        String drawListKey = redisUtils.getDrawListKey(boardId, sheetId);
        redisUtils.appendToList(drawListKey, draw);
        return drawId;
    }


    /**
     * 获取所有笔触数据
     * todo 存在并发问题，需要修改
     * //     * 1. sheetId如果不是全局唯一，需要改动 解决
     * 2. 非原子操作，可能出问题
     */
    public String getDrawOfBoard(Long boardId) {
        String boardKey = redisUtils.getBoardKey(boardId);
        List<String> sheetIds = redisUtils.getListValue(boardKey);
        HashMap<String, Object> drawMap = new HashMap<>();
        for (String sheetId : sheetIds) {
            List<String> drawIds = redisUtils.getListValue(redisUtils.getDrawListKey(boardId, Long.valueOf(sheetId)));
            List<Object> draws = new ArrayList<>();
            for (String drawId : drawIds) {
                String value = redisUtils.getValue(drawId);
                if (value != null) {
                    draws.add(value);
                }
            }
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
        Long sheetId = redisUtils.getId(redisUtils.getSheetKey(boardId), null);
        String boardKey = redisUtils.getBoardKey(boardId);
        return redisUtils.listAdd(boardKey, String.valueOf(sheetId));
    }

    /**
     * 是否删除成功
     *
     * @param boardId
     * @param sheetId
     * @return true redis中存在该数据且已经删除
     * false redis早已经没有该数据，或者从来没有
     */
    public boolean deleteSheet(Long boardId, Long sheetId) {
        String boardKey = redisUtils.getBoardKey(boardId);
        Long count = redisUtils.listRemove(boardKey, String.valueOf(sheetId));
        if (count == 0) {
            return false;
        }
        return redisUtils.delete(redisUtils.getDrawListKey(boardId, sheetId));
    }

    public void deleteBoard(Long boardId) {
        String boardKey = redisUtils.getBoardKey(boardId);
        List<String> sheetIds = redisUtils.getListValue(boardKey);
        for (String sheetId : sheetIds) {
            String drawListKey = redisUtils.getDrawListKey(boardId, Long.valueOf(sheetId));
            List<String> drawIds = redisUtils.getListValue(drawListKey);
            for (String drawId : drawIds) {
                redisUtils.delete(drawId);
            }
            redisUtils.delete(drawListKey);
        }
        redisUtils.delete(boardKey);
    }

    public Boolean deleteDraw(Long drawId) {
        return redisUtils.delete(String.valueOf(drawId));
    }

    public void addSession(Long boardId, Session session) {
        if (!redisUtils.contains("session" + boardId)) {
            redisUtils.setId("admin" + boardId, session.toString());
        }
        redisUtils.listAdd("session" + boardId, session.toString());
    }

    public void delSession(Long boardId, Session session) {
        redisUtils.listRemove("session" + boardId, session.toString());
        if (session.toString().equals(redisUtils.getValue("admin" + boardId))) {
            changeAdmin(boardId);
        }
    }

    public boolean isReadOnly(Long boardId, Session session) {
        String admin = redisUtils.getValue("admin" + boardId);
        return !admin.equals(session.toString());
    }

    public String changeAdmin(Long boardId) {
        String admin = redisUtils.getValue("admin" + boardId);
        redisUtils.listRemove("session" + boardId, admin);
        List<String> sessions = redisUtils.getListValue("session" + boardId);
        if (sessions == null || sessions.size() == 0) {
            deleteBoard(boardId);
            return null;
        }
        String nexAdmin = sessions.get(0);
        redisUtils.setId("admin" + boardId, nexAdmin);
        return nexAdmin;
    }
}
