package com.watermelon.board;

import com.watermelon.board.server.ServerBootStrap;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BoardApplication {
    public static void main(String[] args) {
        SpringApplication.run(BoardApplication.class, args);
        ServerBootStrap bootStrap = new ServerBootStrap();
        bootStrap.initServer();
    }

}
