package dev.kaykyfreitas.task.manager.api.taskmanagerapi.application.usecase.user.delete;

public record DeleteUserCommand(String id) {
    public static DeleteUserCommand with(final String id) {
        return new DeleteUserCommand(id);
    }
}
