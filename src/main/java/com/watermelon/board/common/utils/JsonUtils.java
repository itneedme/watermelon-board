package com.watermelon.board.common.utils;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Scope("singleton")
public class JsonUtils {

    @Autowired
    private JSON json;

    public String MapTOJson(Map map) {
        return json.toJSONString(map);
    }
}
