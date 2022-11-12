package com.watermelon.board.service.handlerImpl;

import com.watermelon.board.service.session.WsSessionManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;


@Component
@Slf4j
public class HttpAuthHandler extends TextWebSocketHandler {
    /**
     * socket 建立成功事件
     *
     * @param session
     * @throws Exception
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info(session.getId() + "建立ws连接");

        Object token = session.getAttributes().get("token");
        if (token != null) {
            // 用户连接成功，放入在线用户缓存
            WsSessionManager.add(token.toString(), session);
            // todo 这里向这个用户发笔触数据


        } else {
            throw new RuntimeException("用户登录已经失效!");
        }
    }

    /**
     * 接收消息事件
     *
     * @param session
     * @param message
     * @throws Exception
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.info("发送文本消息");
        // 获得客户端传来的消息
        String payload = message.getPayload();
        Object token = session.getAttributes().get("token");
        //这里向全部用户广播笔触数据，待做


    }

    /**
     * socket 断开连接时
     *
     * @param session
     * @param status
     * @throws Exception
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("关闭ws连接");
        Object token = session.getAttributes().get("token");
        if (token != null) {
            // 用户退出，移除缓存
            WsSessionManager.remove(token.toString());
            //这里移交管理员权限，待做


        }
    }

    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) {
        log.info("发送二进制消息");
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log.error("异常处理");
        com.watermelon.board.service.session.WsSessionManager.removeAndClose(session.getId());
    }
}

/*通过继承TextWebSocketHandler 类并覆盖相应方法，可以对 websocket 的事件进行处理，这里可以同原生注解的那几个注解连起来看
1.afterConnectionEstablished 方法是在 socket 连接成功后被触发，同原生注解里的 @OnOpen 功能
2.afterConnectionClosed 方法是在 socket 连接关闭后被触发，同原生注解里的 @OnClose 功能
3.handleTextMessage **方法是在客户端发送信息时触发，同原生注解里的 @OnMessage 功能
核心功能都要在这个类来实现*/