package com.watermelon.board.config;

import com.watermelon.board.handler.handlerImpl.DefaultHandler;
import com.watermelon.board.handler.InterceptorImpl.DefaultInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import javax.annotation.Resource;

@Configuration
public class WebSocketConfig implements WebSocketConfigurer {
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

    @Resource
    DefaultHandler defaultHandler;

    @Resource
    DefaultInterceptor defaultInterceptor;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry
                .addHandler(defaultHandler, "/ws") // 自定义处理器
                .addInterceptors(defaultInterceptor) // 自定义拦截器
                .setAllowedOrigins("*"); // 解决跨域问题 [4]
    }
}
