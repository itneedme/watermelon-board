package com.watermelon.board.handler;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import org.apache.tomcat.websocket.WsSession;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;


public class MyWsHandler extends AbstractWebSocketHandler {
    static Log log = LogFactory.get(MyWsHandler.class);

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//        super.afterConnectionEstablished(session);
        log.info("建立ws连接");
    }

}
