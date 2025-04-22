package dev.kaykyfreitas.task.manager.api.taskmanagerapi.application.usecase.user.update;

import dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.exception.NotFoundException;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.exception.NotificationException;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.user.User;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.user.UserGateway;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.user.UserId;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.validation.handler.Notification;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.function.Supplier;

import static java.util.Objects.requireNonNull;

@Component
public class DefaultUpdateUserUseCase extends UpdateUserUseCase {

    private final UserGateway userGateway;

    public DefaultUpdateUserUseCase(final UserGateway userGateway) {
        this.userGateway = requireNonNull(userGateway);
    }

    @Override
    public UpdateUserOutput execute(final UpdateUserCommand command) {
        final var userId = UserId.from(command.id());
        final var name = command.name();
        final var email = command.email();

        final var user = userGateway.findById(userId)
                .orElseThrow(notFound(userId));

        final var notification = Notification.create();
        final var updatedUser = notification.validate(() -> user.update(name, email));

        if (notification.hasError()) notify(notification);

        return UpdateUserOutput.from(userGateway.update(updatedUser));
    }

    private Supplier<NotFoundException> notFound(final UserId id) {
        return () -> NotFoundException.with(User.class, id);
    }

    private void notify(final Notification notification) {
        throw new NotificationException("could not update user", notification);
    }

}
