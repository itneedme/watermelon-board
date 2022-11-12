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
     * 删除白板
     */
    public void deleteBoard() {

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

    /**
     * @return 根据版本判断
     * true 添加成功
     * false 添加失败
     */
    public boolean addDraw() {
        return redisDao.addDraw();
    }

    /**
     * 删除一个笔触
     */
    public void delDraw() {

    }

    /**
     * 判断是否只读
     */
    public boolean isReadOnly() {

        return false;
    }

    /**
     * 管理员更换
     */
    public String changeAdmin(Long boardId) {

        return "";
    }



}
