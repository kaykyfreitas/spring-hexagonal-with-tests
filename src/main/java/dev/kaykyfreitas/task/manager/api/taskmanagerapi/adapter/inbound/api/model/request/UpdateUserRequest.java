package dev.kaykyfreitas.task.manager.api.taskmanagerapi.adapter.inbound.api.model.request;

public record UpdateUserRequest(
        String name,
        String email
) {
}
