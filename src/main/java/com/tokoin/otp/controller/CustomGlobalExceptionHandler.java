package com.tokoin.otp.controller;

import com.tokoin.otp.enums.ResponseStatusType;
import com.tokoin.otp.wrapper.ErrorResponseWrapper;
import com.tokoin.otp.wrapper.ResponseWrapper;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                          HttpHeaders headers,
                                                          HttpStatus status,
                                                          WebRequest request) {


        String error = ex.getBindingResult().getFieldErrors().stream()
                              .map(DefaultMessageSourceResolvable::getDefaultMessage).
                              findFirst().orElse("Required Fields Are missing ");


        ErrorResponseWrapper responseWrapper = new ErrorResponseWrapper("4000", ResponseStatusType.ERROR,
                error, null);


        return new ResponseEntity<>(responseWrapper, status);
    }
}
