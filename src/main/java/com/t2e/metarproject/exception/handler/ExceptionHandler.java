package com.t2e.metarproject.exception.handler;

import com.t2e.metarproject.exception.EntityNotFoundException;
import com.t2e.metarproject.exception.RequestException;
import com.t2e.metarproject.exception.model.ExceptionModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(value = {EntityNotFoundException.class})
    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ExceptionModel exception = new ExceptionModel(ex.getMessage(), LocalDateTime.now(), status);

        return new ResponseEntity<>(exception, status);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = {RequestException.class})
    public ResponseEntity<Object> handleRequestException(RequestException ex){
        HttpStatus status = HttpStatus.NOT_ACCEPTABLE;
        ExceptionModel exception = new ExceptionModel(ex.getMessage(), LocalDateTime.now(), status);

        return new ResponseEntity<>(exception, status);
    }




    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        HttpStatus stat = HttpStatus.UNSUPPORTED_MEDIA_TYPE;
        ExceptionModel exception = new ExceptionModel(ex.getMessage(), LocalDateTime.now(), stat);

        return new ResponseEntity<>(exception, stat);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        HttpStatus stat = HttpStatus.INTERNAL_SERVER_ERROR;
        ExceptionModel exception = new ExceptionModel(ex.getMessage(), LocalDateTime.now(), stat);

        return new ResponseEntity<>(exception, stat);
    }
}
