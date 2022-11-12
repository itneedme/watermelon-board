package com.watermelon.board.service.ot;

import com.watermelon.board.message.IApi;

/**
 * 实现了这个接口的都称为操作
 * 暂时没想出好的实现
 */
public abstract class abstractOperate {
    protected String version;

    public abstract void insert(IApi api);

    public abstract void delete(IApi api);
}
