package dev.kaykyfreitas.task.manager.api.taskmanagerapi.application.usecase.user.retrieve.get;

public record GetUserCommand(String id) {
    public static GetUserCommand with(final String id) {
        return new GetUserCommand(id);
    }
}
