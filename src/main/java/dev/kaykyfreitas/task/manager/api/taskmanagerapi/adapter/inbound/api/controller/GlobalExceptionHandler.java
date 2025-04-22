package dev.kaykyfreitas.task.manager.api.taskmanagerapi.adapter.inbound.api.controller;

import dev.kaykyfreitas.task.manager.api.taskmanagerapi.adapter.inbound.api.model.response.ApiDomainErrorResponse;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.exception.DomainException;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<ApiDomainErrorResponse> handleNotFoundException(final NotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiDomainErrorResponse.from(ex));
    }

    @ExceptionHandler(value = DomainException.class)
    public ResponseEntity<ApiDomainErrorResponse> handleDomainException(final DomainException ex) {
        return ResponseEntity.unprocessableEntity().body(ApiDomainErrorResponse.from(ex));
    }

}
