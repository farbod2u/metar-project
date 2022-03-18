package com.t2e.metarproject.exception.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class ExceptionModel implements Serializable {
    private String message;
    private LocalDateTime timestamp;
    private HttpStatus status;
}
