package com.example.management.exceptoin;


import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ApiExceptionMapper {
    @ExceptionHandler({ApiException.class})
    public ResponseEntity<Object> handleAll(ApiException ex, WebRequest request) {
        return new ResponseEntity<>(ex.toString(), HttpStatusCode.valueOf(ex.getStatusCode()));
    }
}
