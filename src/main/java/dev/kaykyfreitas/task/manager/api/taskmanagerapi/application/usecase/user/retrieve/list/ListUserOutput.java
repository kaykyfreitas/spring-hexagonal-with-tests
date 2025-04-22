package dev.kaykyfreitas.task.manager.api.taskmanagerapi.application.usecase.user.retrieve.list;

import dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.user.User;

public record ListUserOutput(
        String id,
        String name,
        String username,
        String email
) {
    public static ListUserOutput from(final User user) {
        return new ListUserOutput(
                user.getId().getValue(),
                user.getName(),
                user.getUsername(),
                user.getEmail()
        );
    }
}
