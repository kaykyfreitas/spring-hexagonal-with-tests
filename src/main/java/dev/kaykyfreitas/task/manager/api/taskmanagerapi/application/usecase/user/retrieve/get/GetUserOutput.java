package dev.kaykyfreitas.task.manager.api.taskmanagerapi.application.usecase.user.retrieve.get;

import dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.user.User;

import java.time.Instant;

public record GetUserOutput(
        String id,
        String name,
        String username,
        String email,
        Instant createdAt,
        Instant updatedAt
) {
    public static GetUserOutput from(final User user) {
        return new GetUserOutput(
                user.getId().getValue(),
                user.getName(),
                user.getUsername(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}
