package dev.kaykyfreitas.task.manager.api.taskmanagerapi.application.usecase.user.create;

public record CreateUserCommand(String name, String username, String email) {
    public static CreateUserCommand with(final String name, final String username, final String email) {
        return new CreateUserCommand(name, username, email);
    }
}
