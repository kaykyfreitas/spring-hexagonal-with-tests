package dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.user;

import dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.pagination.Pagination;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.pagination.SearchQuery;

import java.util.Optional;

public interface UserGateway {
    User create(User user);
    User update(User user);
    void deleteById(UserId userId);
    Optional<User> findById(UserId userId);
    Pagination<User> findAll(SearchQuery query);
}
