package com.watermelon.board.common.utils;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Scope("singleton")
public class JsonUtils {

    public String MapTOJson(Map map) {
        return JSON.toJSONString(map);
    }
}
