package com.watermelon.board.service;

import com.watermelon.board.dao.RedisDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 关于白板的业务类
 */
@Service
public class BoardService {

    @Autowired
    private RedisDao redisDao;

    /**
     * 新建白板
     *
     * @return 白板id
     */
    public Long createBoard() {
        // 向redis中增加该画板
        return redisDao.createBoard();
    }

    /**
     * 加入新用户，返回所有笔触
     *
     * @return 一个包含了该board所有数据的json
     * 该json结构是一个map<sheetId,Object(DrawType[])
     */
    public String getDrawDataOfBoard(Long boardId) {
        return redisDao.getDrawOfBoard(boardId);
    }

    public boolean addDraw() {
        return redisDao.addDraw();
    }
}
