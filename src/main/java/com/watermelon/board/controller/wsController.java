package com.watermelon.board.controller;

import com.watermelon.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.flyway.FlywayDataSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController("/watermelon")
public class wsController {

    @Autowired
    private BoardService boardService;

    /**
     * new board
     */
    @GetMapping("/createBoard")
    public Long createBoard() {
        return boardService.createBoard();
    }

}
