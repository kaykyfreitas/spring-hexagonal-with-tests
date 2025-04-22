package dev.kaykyfreitas.task.manager.api.taskmanagerapi.adapter.inbound.api.controller;

import dev.kaykyfreitas.task.manager.api.taskmanagerapi.adapter.inbound.api.UserRestAPI;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.adapter.inbound.api.model.request.CreateUserRequest;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.adapter.inbound.api.model.request.UpdateUserRequest;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.adapter.inbound.api.model.response.CreateUserResponse;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.adapter.inbound.api.model.response.GetUserResponse;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.adapter.inbound.api.model.response.ListUserResponse;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.adapter.inbound.api.model.response.UpdateUserResponse;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.adapter.inbound.api.presenter.UserRestApiPresenter;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.application.usecase.user.create.CreateUserCommand;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.application.usecase.user.create.CreateUserUseCase;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.application.usecase.user.delete.DeleteUserCommand;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.application.usecase.user.delete.DeleteUserUseCase;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.application.usecase.user.retrieve.get.GetUserCommand;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.application.usecase.user.retrieve.get.GetUserUseCase;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.application.usecase.user.retrieve.list.ListUsersUseCase;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.application.usecase.user.update.UpdateUserCommand;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.application.usecase.user.update.UpdateUserUseCase;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.pagination.Pagination;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.pagination.SearchQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class UserRestController implements UserRestAPI {

    private final CreateUserUseCase createUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final GetUserUseCase getUserUseCase;
    private final ListUsersUseCase listUsersUseCase;
    private final DeleteUserUseCase deleteUserUseCase;

    @Override
    public ResponseEntity<CreateUserResponse> create(final CreateUserRequest request) {
        final var command = CreateUserCommand.with(
                request.name(),
                request.username(),
                request.email()
        );

        final var output = createUserUseCase.execute(command);

        return ResponseEntity.created(URI.create("/users/" + output.id())).body(new CreateUserResponse(output.id()));
    }

    @Override
    public ResponseEntity<UpdateUserResponse> update(final String id, final UpdateUserRequest request) {
        final var command = UpdateUserCommand.with(
                id,
                request.name(),
                request.email()
        );

        final var output = updateUserUseCase.execute(command);

        return ResponseEntity.status(HttpStatus.OK).body(new UpdateUserResponse(output.id()));
    }

    @Override
    public ResponseEntity<GetUserResponse> getById(final String id) {
        final var command = GetUserCommand.with(id);

        final var output = getUserUseCase.execute(command);

        return ResponseEntity.status(HttpStatus.OK).body(UserRestApiPresenter.present(output));
    }

    @Override
    public ResponseEntity<Pagination<ListUserResponse>> list(
            final String search,
            final int page,
            final int perPage,
            final String sort,
            final String direction
    ) {
        final var query = new SearchQuery(page, perPage, search, sort, direction);

        final var output = listUsersUseCase.execute(query);

        return ResponseEntity.status(HttpStatus.OK).body(output.map(UserRestApiPresenter::present));
    }

    @Override
    public void deleteById(final String id) {
        final var command = DeleteUserCommand.with(id);

        deleteUserUseCase.execute(command);
    }

}
