package dev.kaykyfreitas.task.manager.api.taskmanagerapi.application.usecase.user.create;

import dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.user.User;

public record CreateUserOutput(String id) {
    public static CreateUserOutput from(final String anId) {
        return new CreateUserOutput(anId);
    }

    public static CreateUserOutput from(final User aUser) {
        return new CreateUserOutput(aUser.getId().getValue());
    }
}
