package com.watermelon.board.config;

import com.watermelon.board.handler.InterceptorImpl.DefaultInterceptor;
import com.watermelon.board.handler.handlerImpl.HttpAuthHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.config.annotation.EnableWebSocket;


import javax.annotation.Resource;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    @Autowired
    private HttpAuthHandler httpAuthHandler;
    @Resource
    private DefaultInterceptor defaultInterceptor;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry
                .addHandler(httpAuthHandler, "/myWS")
                .addInterceptors(defaultInterceptor) // 自定义拦截器
                .setAllowedOrigins("*");
    }
}

/*
添加注解 @EnableWebSocket 表示开启了webocket服务
registerWebSocketHandlers 这个方法用来注册websocket服务，上面表示将路径 myWS的请求转发到 WebSocketHandler
etAllowedOrigins("*") **这个是关闭跨域校验，方便本地调试*/