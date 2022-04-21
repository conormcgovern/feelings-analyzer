package com.example.feelingsanalyzer.exception;

import com.example.feelingsanalyzer.model.Message;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Optional;

@ControllerAdvice
public class DefaultExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    @ResponseBody
    public Message handleBadRequest(BadRequestException e) {
        String message = Optional.ofNullable(e.getMessage()).orElse("Bad Request");
        return new Message(message);
    }
}
