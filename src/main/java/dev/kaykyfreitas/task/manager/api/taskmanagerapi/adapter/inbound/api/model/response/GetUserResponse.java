package dev.kaykyfreitas.task.manager.api.taskmanagerapi.adapter.inbound.api.model.response;

import java.time.Instant;

public record GetUserResponse(
        String id,
        String name,
        String username,
        String email,
        Instant createdAt,
        Instant updatedAt
) {
}
