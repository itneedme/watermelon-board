package com.watermelon.board.controller;

import com.watermelon.board.message.WsMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class WsController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;


    //发送广播通知
    @MessageMapping("/addNotice")   //接收客户端发来的消息，客户端发送消息地址为：/app/addNotice
    @SendTo("/topic/notice")        //向客户端发送广播消息（方式一），客户端订阅消息地址为：/topic/notice
    public WsMessage notice(String notice, Principal fromUser) {
        //TODO 业务处理

        return new WsMessage();
    }

    //发送点对点消息
    @MessageMapping("/msg")         //接收客户端发来的消息，客户端发送消息地址为：/app/msg
    @SendToUser("/queue/msg/result") //向当前发消息客户端（就是自己）发送消息的发送结果，客户端订阅消息地址为：/user/queue/msg/result
    public boolean sendMsg(WsMessage message, Principal fromUser){
        //TODO 业务处理
//        message.setFromName(fromUser.getName());
//
//        //向指定客户端发送消息，第一个参数Principal.name为前面websocket握手认证通过的用户name（全局唯一的），客户端订阅消息地址为：/user/queue/msg/new
//        messagingTemplate.convertAndSendToUser(message.getToName(), "/queue/msg/new", message);
        return true;
    }
}
