package dev.kaykyfreitas.task.manager.api.taskmanagerapi.application.usecase.user.retrieve.list;

import dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.pagination.Pagination;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.pagination.SearchQuery;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.user.UserGateway;
import org.springframework.stereotype.Component;

import static java.util.Objects.requireNonNull;

@Component
public class DefaultListUsersUseCase extends ListUsersUseCase {

    private final UserGateway userGateway;

    public DefaultListUsersUseCase(final UserGateway userGateway) {
        this.userGateway = requireNonNull(userGateway);
    }

    @Override
    public Pagination<ListUserOutput> execute(final SearchQuery searchQuery) {
        return userGateway.findAll(searchQuery)
                .map(ListUserOutput::from);
    }

}
