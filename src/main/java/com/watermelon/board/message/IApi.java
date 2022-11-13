package com.watermelon.board.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

@Data
@EnableAutoConfiguration
@AllArgsConstructor
public class IApi {
    St st;
    long sheetId;
    long userVersion;
    String userId;
    Object data;
}

