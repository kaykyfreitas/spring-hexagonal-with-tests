package dev.kaykyfreitas.task.manager.api.taskmanagerapi.adapter.inbound.api.model.response;

public record ListUserResponse(
        String id,
        String name,
        String username,
        String email
) {
}
