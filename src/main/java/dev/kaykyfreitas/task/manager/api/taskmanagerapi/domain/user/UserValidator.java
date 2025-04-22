package dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.user;

import dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.validation.DomainError;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.validation.ValidationHandler;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.validation.Validator;

import java.util.Objects;

public class UserValidator extends Validator {

    private static final Integer NAME_MAX_LENGTH = 100;
    private static final Integer NAME_MIN_LENGTH = 3;

    private static final Integer USERNAME_MAX_LENGTH = 15;
    private static final Integer USERNAME_MIN_LENGTH = 3;

    private static final String EMAIL_FORMAT_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

    private final User user;

    protected UserValidator(final User user, final ValidationHandler handler) {
        super(handler);
        this.user = user;
    }

    @Override
    public void validate() {
        checkNameConstraints();
        checkUsernameConstraints();
        checkEmailConstraints();
        checkCreatedAtConstraints();
        checkUpdatedAtConstraints();
    }

    private void checkNameConstraints() {
        final var name = this.user.getName();
        if (Objects.isNull(name)) {
            this.validationHandler().append(new DomainError("'name' should not be null"));
            return;
        }

        if (name.isBlank()) {
            this.validationHandler().append(new DomainError("'name' should not be empty"));
            return;
        }

        final int length = name.trim().length();
        if (length > NAME_MAX_LENGTH || length < NAME_MIN_LENGTH) {
            final String msg = "'name' must be between %d and %d characters";
            this.validationHandler().append(new DomainError(String.format(msg, NAME_MIN_LENGTH, NAME_MAX_LENGTH)));
        }
    }

    private void checkUsernameConstraints() {
        final var username = this.user.getUsername();
        if (Objects.isNull(username)) {
            this.validationHandler().append(new DomainError("'username' should not be null"));
            return;
        }

        if (username.isBlank()) {
            this.validationHandler().append(new DomainError("'username' should not be empty"));
            return;
        }

        final int length = username.trim().length();
        if (length > USERNAME_MAX_LENGTH || length < USERNAME_MIN_LENGTH) {
            final String msg = "'username' must be between %d and %d characters";
            this.validationHandler().append(new DomainError(String.format(msg, USERNAME_MIN_LENGTH, USERNAME_MAX_LENGTH)));
        }
    }

    private void checkEmailConstraints() {
        final var email = this.user.getEmail();
        if (Objects.isNull(email)) {
            this.validationHandler().append(new DomainError("'email' should not be null"));
            return;
        }

        if (email.isBlank()) {
            this.validationHandler().append(new DomainError("'email' should not be empty"));
            return;
        }

        if (!email.matches(EMAIL_FORMAT_REGEX)) {
            this.validationHandler().append(new DomainError("'email' format is not valid"));
        }
    }

    private void checkCreatedAtConstraints() {
        if (Objects.isNull(this.user.getCreatedAt())) {
            this.validationHandler().append(new DomainError("'createdAt' should not be null"));
        }
    }

    private void checkUpdatedAtConstraints() {
        if (Objects.isNull(this.user.getUpdatedAt())) {
            this.validationHandler().append(new DomainError("'updatedAt' should not be null"));
        }
    }

}
