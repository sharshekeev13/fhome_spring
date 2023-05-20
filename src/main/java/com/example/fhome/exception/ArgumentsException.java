package com.example.fhome.exception;


import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

public class ArgumentsException {
    private final String message;
    private final Map<String, String> errors;
    private final HttpStatus httpStatus;
    private final ZonedDateTime timeStamp;

    public ArgumentsException(String message, HttpStatus httpStatus, ZonedDateTime timeStamp, Map<String, String> errors) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.timeStamp = timeStamp;
        this.errors = errors;
    }

    public String getMessage() {
        return message;
    }

    public Map<String, String> getErrors() {
        return errors;
    }
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public ZonedDateTime getTimeStamp() {
        return timeStamp;
    }

}