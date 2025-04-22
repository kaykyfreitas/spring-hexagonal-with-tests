package dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.exception;

import dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.Entity;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.Identifier;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.validation.DomainError;

import java.util.Collections;
import java.util.List;

public class NotFoundException extends DomainException{

    protected NotFoundException(final String aMessage, final List<DomainError> someErrors) {
        super(aMessage, someErrors);
    }

    public static NotFoundException with(
            final Class<? extends Entity<?>> entity,
            final Identifier id
    ) {
        final var anError = "%s with id %s was not found".formatted(
                entity.getSimpleName().toLowerCase(),
                id.getValue()
        );
        return new NotFoundException(anError, Collections.emptyList());
    }

    public static NotFoundException with(final DomainError anError) {
        return new NotFoundException(anError.message(), List.of(anError));
    }

}
