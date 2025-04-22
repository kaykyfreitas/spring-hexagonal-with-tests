package dev.kaykyfreitas.task.manager.api.taskmanagerapi.application.usecase.user.update;

public record UpdateUserCommand(String id, String name, String email) {
    public static UpdateUserCommand with(final String id, final String name, final String email) {
        return new UpdateUserCommand(id, name, email);
    }
}
