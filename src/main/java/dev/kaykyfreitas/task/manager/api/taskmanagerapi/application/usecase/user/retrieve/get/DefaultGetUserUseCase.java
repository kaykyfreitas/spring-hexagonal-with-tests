package dev.kaykyfreitas.task.manager.api.taskmanagerapi.application.usecase.user.retrieve.get;

import dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.exception.NotFoundException;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.user.User;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.user.UserGateway;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.user.UserId;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

import static java.util.Objects.requireNonNull;

@Component
public class DefaultGetUserUseCase extends GetUserUseCase {

    private final UserGateway userGateway;

    public DefaultGetUserUseCase(final UserGateway userGateway) {
        this.userGateway = requireNonNull(userGateway);
    }

    @Override
    public GetUserOutput execute(final GetUserCommand command) {
        final UserId userId = UserId.from(command.id());

        return this.userGateway.findById(userId)
                .map(GetUserOutput::from)
                .orElseThrow(notFound(userId));
    }

    private Supplier<NotFoundException> notFound(final UserId id) {
        return () -> NotFoundException.with(User.class, id);
    }

}
