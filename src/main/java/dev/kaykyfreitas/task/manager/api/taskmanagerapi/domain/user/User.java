package dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.user;

import dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.AggregateRoot;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.exception.NotificationException;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.utils.InstantUtils;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.validation.ValidationHandler;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.validation.handler.Notification;

import java.time.Instant;

public class User extends AggregateRoot<UserId> {

    private String name;
    private final String username;
    private String email;
    private final Instant createdAt;
    private Instant updatedAt;

    private User(
            final UserId id,
            final String name,
            final String username,
            final String email,
            final Instant createdAt,
            final Instant updatedAt
    ) {
        super(id);
        this.name = name;
        this.username = username;
        this.email = email;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.selfValidate();
    }

    public static User newUser(
            final String name,
            final String username,
            final String email
    ) {
        final var id = UserId.unique();
        final var now = InstantUtils.now();
        return new User(id, name, username, email, now, now);
    }

    public static User with(
            final UserId id,
            final String name,
            final String username,
            final String email,
            final Instant createdAt,
            final Instant updatedAt
    ) {
        return new User(id, name, username, email, createdAt, updatedAt);
    }

    public static User with(final User aUser) {
        return with(
                aUser.id,
                aUser.name,
                aUser.username,
                aUser.email,
                aUser.createdAt,
                aUser.updatedAt
        );
    }

    @Override
    public void validate(final ValidationHandler handler) {
        new UserValidator(this, handler).validate();
    }

    private void selfValidate() {
        final var notification = Notification.create();

        this.validate(notification);

        if (notification.hasError())
            throw new NotificationException("failed to create user", notification);
    }

    public User update(final String name, final String email) {
        this.name = name;
        this.email = email;
        this.updatedAt = InstantUtils.now();
        this.selfValidate();
        return this;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

}
