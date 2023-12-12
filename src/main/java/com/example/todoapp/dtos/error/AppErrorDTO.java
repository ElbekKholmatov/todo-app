package com.example.todoapp.dtos.error;

import lombok.Getter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Getter
public class AppErrorDTO implements BaseDTO {
    private final String path;
    private final String message;
    private final int status;
    private final Object body;
    private final String timestamp;


    public AppErrorDTO(String path, String message, int status) {
        this(path, message, null, status);
    }

    public AppErrorDTO(String path, String message, Object body, int status) {
        this.path = path;
        this.message = message;
        this.body = body;
        this.status = status;
        this.timestamp = LocalDateTime.now(ZoneId.of("Asia/Tashkent")).format(DateTimeFormatter.ISO_DATE_TIME);
    }

}