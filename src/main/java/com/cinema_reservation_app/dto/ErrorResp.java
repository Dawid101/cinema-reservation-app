package com.cinema_reservation_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ErrorResp {
    private String errorCode;
    private String msg;
    private LocalDateTime timeStamp;
}
