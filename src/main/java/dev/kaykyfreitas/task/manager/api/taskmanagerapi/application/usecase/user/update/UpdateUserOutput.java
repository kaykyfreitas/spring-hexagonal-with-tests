package dev.kaykyfreitas.task.manager.api.taskmanagerapi.application.usecase.user.update;

import dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.user.User;

public record UpdateUserOutput(String id) {
    public static UpdateUserOutput from(final String id) {
        return new UpdateUserOutput(id);
    }
    public static UpdateUserOutput from(final User user) {
        return new UpdateUserOutput(user.getId().getValue());
    }
}
