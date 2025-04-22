package dev.kaykyfreitas.task.manager.api.taskmanagerapi.application.usecase.user.create;

import dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.exception.NotificationException;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.user.User;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.user.UserGateway;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.validation.handler.Notification;
import org.springframework.stereotype.Component;

import static java.util.Objects.requireNonNull;

@Component
public class DefaultCreateUserUseCase extends CreateUserUseCase {

    private final UserGateway userGateway;

    public DefaultCreateUserUseCase(final UserGateway userGateway) {
        this.userGateway = requireNonNull(userGateway);
    }

    @Override
    public CreateUserOutput execute(final CreateUserCommand command) {
        final var name = command.name();
        final var username = command.username();
        final var email = command.email();

        final var notification = Notification.create();
        final var user = notification.validate(() -> User.newUser(name, username, email));

        if (notification.hasError()) notify(notification);

        return CreateUserOutput.from(this.userGateway.create(user));
    }

    private void notify(final Notification notification) {
        throw new NotificationException("could not create an user", notification);
    }

}
