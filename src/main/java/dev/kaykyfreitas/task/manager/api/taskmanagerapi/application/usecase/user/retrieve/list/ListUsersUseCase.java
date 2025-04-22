package dev.kaykyfreitas.task.manager.api.taskmanagerapi.application.usecase.user.retrieve.list;

import dev.kaykyfreitas.task.manager.api.taskmanagerapi.application.UseCase;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.pagination.Pagination;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.pagination.SearchQuery;

public abstract class ListUsersUseCase extends UseCase<SearchQuery, Pagination<ListUserOutput>> {
}
