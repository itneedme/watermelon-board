package com.watermelon.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController("/watermelon")
public class wsController {
    /**
     * new board
     */
    @GetMapping("/createBoard")
    public String createBoard() {
        return "";
    }

}
