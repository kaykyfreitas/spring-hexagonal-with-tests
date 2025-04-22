package dev.kaykyfreitas.task.manager.api.taskmanagerapi.adapter.inbound.api.model.response;

import dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.exception.DomainException;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.validation.DomainError;

import java.util.List;

public record ApiDomainErrorResponse(String message, List<DomainError> errors) {
    public static ApiDomainErrorResponse from(DomainException ex) {
        return new ApiDomainErrorResponse(ex.getMessage(), ex.getErrors());
    }
}
