package com.watermelon.board.service.session;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


@Slf4j
public class WsSessionManager {
    /**
     * 保存连接 session 的地方
     */
    private static ConcurrentHashMap<String, WebSocketSession> SESSION_POOL = new ConcurrentHashMap<>();
    /**
     * 添加 session
     *
     * @param key
     */
    public static void add(String key, WebSocketSession session) {
        // 添加 session
        SESSION_POOL.put(key, session);
    }
    /**
     * 删除 session,会返回删除的 session
     *
     * @param key
     * @return
     */
    public static WebSocketSession remove(String key) {
        // 删除 session
        return SESSION_POOL.remove(key);
    }
    /**
     * 删除并同步关闭连接
     *
     * @param key
     */
    public static void removeAndClose(String key) {
        WebSocketSession session = remove(key);
        if (session != null) {
            try {
                // 关闭连接
                session.close();
            } catch (IOException e) {
                // todo: 关闭出现异常处理
                e.printStackTrace();
            }
        }
    }
    /**
     * 获得 session
     *
     * @param key
     * @return
     */
    public static WebSocketSession get(String key) {
        // 获得 session

        return SESSION_POOL.get(key);
    }
    /**
     * 发送消息给指定用户
     *
     * @param umac
     * @param message
     */

    public static void sendToUser(String umac,String message) throws IOException {
        WebSocketSession webSocketSession = get(umac);
        if(!webSocketSession.isOpen()){
            return;
        }
        synchronized (umac) {
            webSocketSession.sendMessage(new TextMessage(message));
        }
    }


    /**
     * 发送消息给所有用户
     *
     * @param message
     */

    public static void sendToAll(String message) throws IOException {
        Set set =  SESSION_POOL.keySet();
        Iterator iterator = set.iterator();
        while (iterator.hasNext()){
            String key = (String) iterator.next();
            WebSocketSession webSocketSession = get(key);
            if(!webSocketSession.isOpen()){
                continue;
            }
            synchronized (key) {
                webSocketSession.sendMessage(new TextMessage(message));
            }
        }
    }
}

/*这里简单通过ConcurrentHashMap来实现了一个 session 池，用来保存已经登录的 web socket 的  session。
 服务端发送消息给客户端必须要通过这个 session。
 */