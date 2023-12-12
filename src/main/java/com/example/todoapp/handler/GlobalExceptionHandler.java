package com.example.todoapp.handler;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import org.jetbrains.annotations.NotNull;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.example.todoapp.dtos.error.AppErrorDTO;
import com.example.todoapp.exceptions.DuplicateEntryFoundException;
import com.example.todoapp.exceptions.ItemNotFoundException;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<AppErrorDTO> handleException(Exception e, HttpServletRequest request) {
        logException(e);
        return buildErrorResponse(request.getRequestURI(), e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({MalformedJwtException.class, ExpiredJwtException.class, SignatureException.class})
    public ResponseEntity<AppErrorDTO> handleJwtRelatedException(Exception e, HttpServletRequest request) {
        return buildErrorResponse(request.getRequestURI(), e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<AppErrorDTO> handleJwtRelatedException(AccessDeniedException e, HttpServletRequest request){
        return buildErrorResponse(request.getRequestURI(), e.getMessage(), HttpStatus.FORBIDDEN);
    }


    @ExceptionHandler({DuplicateEntryFoundException.class, DataIntegrityViolationException.class})
    public ResponseEntity<AppErrorDTO> handleDuplicateEntryFoundException(Exception e, HttpServletRequest request) {
        logException(e);
        return buildErrorResponse(request.getRequestURI(), e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<AppErrorDTO> handleItemNotFoundException(ItemNotFoundException e, HttpServletRequest request) {
        logException(e);
        return buildErrorResponse(request.getRequestURI(), e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<AppErrorDTO> handleIllegalArgumentException(Exception e, HttpServletRequest request) {
        logException(e);
        return buildErrorResponse(request.getRequestURI(), e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnsupportedOperationException.class)
    public ResponseEntity<AppErrorDTO> handleUnsupportedOperationException(Exception e, HttpServletRequest request) {
        logException(e);
        String errorMessage = "Unsupported operation";
        return buildErrorResponse(request.getRequestURI(), errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<AppErrorDTO> handleDateTimeParseException(Exception e, HttpServletRequest request) {
        logException(e);
        String errorMessage = "Invalid DateTime also support [yyyy-mm-dd] format";
        return buildErrorResponse(request.getRequestURI(), errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<AppErrorDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
        logException(e);
        String errorMessage = "Input is not valid";
        Map<String, List<String>> errorBody = extractFieldErrors(e);
        return buildErrorResponse(request.getRequestURI(), errorMessage, errorBody);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<AppErrorDTO> handleNullPointerException(NullPointerException e, @NotNull HttpServletRequest request) {
        logException(e);
        String errorMessage = "Input is not valid";
        return buildErrorResponse(request.getRequestURI(), errorMessage, HttpStatus.BAD_REQUEST);
    }

    private void logException(Exception e) {
        CompletableFuture.runAsync(e::printStackTrace);
    }

    private ResponseEntity<AppErrorDTO> buildErrorResponse(String requestURI, String errorMessage, HttpStatus httpStatus) {
        return ResponseEntity.status(httpStatus).body(new AppErrorDTO(requestURI, errorMessage, httpStatus.value()));
    }

    private ResponseEntity<AppErrorDTO> buildErrorResponse(String requestURI, String errorMessage, Map<String, List<String>> errorBody) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new AppErrorDTO(requestURI, errorMessage, errorBody, HttpStatus.BAD_REQUEST.value()));
    }

    private Map<String, List<String>> extractFieldErrors(MethodArgumentNotValidException e) {
        Map<String, List<String>> errorBody = new HashMap<>();
        for (FieldError fieldError : e.getFieldErrors()) {
            errorBody.computeIfAbsent(fieldError.getField(), k -> new ArrayList<>()).add(fieldError.getDefaultMessage());
        }
        return errorBody;
    }
}
