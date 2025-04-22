package dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.pagination;

public record SearchQuery(
        int page,
        int perPage,
        String terms,
        String sort,
        String direction
) {
}