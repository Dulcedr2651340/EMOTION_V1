package com.music.Emotion.exception;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
public class ErrorResponse {

    private String code;
    private HttpStatus status;
    private String message;
    private LocalDateTime timestamp;
    private List<String> detailMessages;
}
