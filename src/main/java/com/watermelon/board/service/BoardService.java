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
     * todo 删除白板
     */
    public void deleteBoard(Long boardId) {
        redisDao.deleteBoard(boardId);
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
     * todo 增添笔触
     *
     * @param boardId 白板id
     * @param draw 笔触内容 IDrawAPI中的IDrawType
     * @return 根据版本判断
     * 添加成功 返回DrawID
     * 添加失败 null
     */
    public String addDraw(Long boardId, String draw) {
        return redisDao.addDraw();
    }

    /**
     * todo 删除一个笔触
     * @param drawId 白板id
     */
    public void delDraw(Long drawId) {

    }


    /**
     * todo 判断对于该用户是否只读
     * @param drawId 笔触id
     * @param session 用户session
     * @return 是否只读
     */
    public boolean isReadOnly(Long drawId, String session) {

        return false;
    }

    /**
     * todo 管理员更换
     *
     * @param boardId 白板id
     * @return 返回指定下个管理员的session
     * 若为null，则该画板已经没有人
     */
    public String changeAdmin(Long boardId) {

        return "";
    }

}
