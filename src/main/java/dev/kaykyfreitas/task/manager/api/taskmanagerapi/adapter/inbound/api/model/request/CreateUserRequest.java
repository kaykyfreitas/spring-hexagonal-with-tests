package dev.kaykyfreitas.task.manager.api.taskmanagerapi.adapter.inbound.api.model.request;

public record CreateUserRequest(
        String name,
        String username,
        String email
) {
}
