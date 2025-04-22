package dev.kaykyfreitas.task.manager.api.taskmanagerapi.adapter.inbound.api.presenter;

import dev.kaykyfreitas.task.manager.api.taskmanagerapi.adapter.inbound.api.model.response.GetUserResponse;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.adapter.inbound.api.model.response.ListUserResponse;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.application.usecase.user.retrieve.get.GetUserOutput;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.application.usecase.user.retrieve.list.ListUserOutput;

public interface UserRestApiPresenter {

    static GetUserResponse present(final GetUserOutput output) {
        return new GetUserResponse(
                output.id(),
                output.name(),
                output.username(),
                output.email(),
                output.createdAt(),
                output.updatedAt()
        );
    }

    static ListUserResponse present(final ListUserOutput output) {
        return new ListUserResponse(
                output.id(),
                output.name(),
                output.username(),
                output.email()
        );
    }
}
