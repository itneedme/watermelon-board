package com.watermelon.board.service.handlerImpl;

import net.sf.json.JSONArray;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.watermelon.board.message.IApi;
import com.watermelon.board.message.St;
import com.watermelon.board.service.BoardService;
import com.watermelon.board.service.session.WsSessionManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.List;
import java.util.Set;


@Component
@Slf4j
public class HttpAuthHandler extends TextWebSocketHandler {

    @Autowired
    private BoardService boardService;

    /**
     * socket 建立成功事件
     *
     * @param session
     * @throws Exception
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info(session.getId() + "建立ws连接");
        Object boardId = session.getAttributes().get("boardId");
        if (boardId != null) {
            // 用户连接成功，放入在线用户缓存
            WsSessionManager.add(boardId.toString(), session);
            // todo 这里向这个用户发笔触数据
//            List<Long> boardlist = boardService.getAllBoard();
//            for(Long boardId1 : boardlist){
//
//                JSONArray json = JSONArray.fromObject(boardService.getDrawDataOfBoard(boardId1));
//                String draw = json.toString();//把json转换为String
//                session.sendMessage(new TextMessage(draw));
//            }
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
        Object boardId = session.getAttributes().get("boardId");
        //这里向全部用户广播笔触数据，待做
        if (StringUtils.isNotBlank(payload)) {
            //解析发送的报文
            JSONObject jsonObject = JSON.parseObject(payload);
            IApi iApi = jsonObject.toJavaObject(IApi.class);
            St st = iApi.getSt();
            Long sheetId = iApi.getSheetId();
            Long userVersion = iApi.getUserVersion();
            String userid = iApi.getUserId();
            Object data = iApi.getData();
            // todo IApi里应该添加一个boardId
//            Long boardId =null; //todo 初始化之后会改
            boolean readOnly = boardService.isReadOnly((Long) boardId, (Session) session);
            //判断是否可读
            if (readOnly) {
                switch (st) {
                    case draw:
                        //todo 这里应该要获取一个服务器自身版本draw
                        String draw = null; //todo 初始化之后会改
                        Long newVersion = boardService.addDraw((Long) boardId, sheetId, draw, userVersion);
                        if (newVersion != null) {
                            WsSessionManager.sendToAll(payload);
                        }else {
                            //todo 失败后发给客户所有笔触数据更新版本

//                            List<Long> boardlist = boardService.getAllBoard();
//                            for(Long boardId1 : boardlist){
//                                JSONArray json = JSONArray.fromObject(boardService.getDrawDataOfBoard(boardId1));
//                                String draw1 = json.toString();//把json转换为String
//                                session.sendMessage(new TextMessage(draw1));
//                            }
                            break;
                        }
                        break;
                    case delete:
                        boolean del = boardService.delDraw((Long) boardId);
                        if(del){
                            session.sendMessage(new TextMessage("删除成功"));
                        }else {
                            session.sendMessage(new TextMessage("删除失败"));
                        }
                        break;
                    case addsheet:
                        Long addsheet = boardService.addSheet((Long) boardId);
                        if(addsheet != null){
                            session.sendMessage(new TextMessage("建表成功"));
                        }else {
                            session.sendMessage(new TextMessage("建表失败"));
                        }
                        break;
//                    case delsheet:

                }
            } else {
                session.sendMessage(new TextMessage("当前白板只读"));
            }

        }


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
        Object boardId = session.getAttributes().get("boardId");
        if (boardId != null) {
            String change = boardService.changeAdmin((Long) boardId);
            // 用户退出，移除缓存
            WsSessionManager.sendToUser((String) boardId,change);
            WsSessionManager.remove(boardId.toString());
            boardService.delSession((Long) boardId,(Session) session);
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