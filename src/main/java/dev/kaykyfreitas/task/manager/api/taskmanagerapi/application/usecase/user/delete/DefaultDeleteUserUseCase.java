package dev.kaykyfreitas.task.manager.api.taskmanagerapi.application.usecase.user.delete;

import dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.user.UserGateway;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.user.UserId;
import org.springframework.stereotype.Component;

import static java.util.Objects.requireNonNull;

@Component
public class DefaultDeleteUserUseCase extends DeleteUserUseCase {

    private final UserGateway userGateway;

    public DefaultDeleteUserUseCase(final UserGateway userGateway) {
        this.userGateway = requireNonNull(userGateway);
    }

    @Override
    public void execute(final DeleteUserCommand command) {
        final var userId = UserId.from(command.id());
        userGateway.deleteById(userId);
    }

}
