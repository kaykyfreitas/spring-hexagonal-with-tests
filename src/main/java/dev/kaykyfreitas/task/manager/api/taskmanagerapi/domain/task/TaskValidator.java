package dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.task;

import dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.validation.DomainError;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.validation.ValidationHandler;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.validation.Validator;

import java.util.Objects;

public class TaskValidator extends Validator {

    private static final Integer TITLE_MAX_LENGTH = 50;
    private static final Integer TITLE_MIN_LENGTH = 2;

    private static final Integer DESCRIPTION_MAX_LENGTH = 500;
    private static final Integer DESCRIPTION_MIN_LENGTH = 2;


    private final Task task;

    public TaskValidator(final Task task, final ValidationHandler handler) {
        super(handler);
        this.task = task;
    }

    @Override
    public void validate() {
        checkTitleConstraints();
        checkDescriptionConstraints();
        checkUserIdConstraints();
        checkCreatedAtConstraints();
        checkUpdatedAtConstraints();
    }

    private void checkTitleConstraints() {
        final var title = this.task.getTitle();
        if (Objects.isNull(title)) {
            this.validationHandler().append(new DomainError("'title' should not be null"));
            return;
        }

        if (title.isBlank()) {
            this.validationHandler().append(new DomainError("'title' should not be empty"));
            return;
        }

        final int length = title.trim().length();
        if (length > TITLE_MAX_LENGTH || length < TITLE_MIN_LENGTH) {
            final String msg = "'title' must be between %d and %d characters";
            this.validationHandler().append(new DomainError(String.format(msg, TITLE_MIN_LENGTH, TITLE_MAX_LENGTH)));
        }
    }

    private void checkDescriptionConstraints() {
        final var description = this.task.getDescription();
        if (Objects.isNull(description)) {
            this.validationHandler().append(new DomainError("'description' should not be null"));
            return;
        }

        if (description.isBlank()) {
            this.validationHandler().append(new DomainError("'description' should not be empty"));
            return;
        }

        final int length = description.trim().length();
        if (length > DESCRIPTION_MAX_LENGTH || length < DESCRIPTION_MIN_LENGTH) {
            final String msg = "'description' must be between %d and %d characters";
            this.validationHandler().append(new DomainError(String.format(msg, DESCRIPTION_MIN_LENGTH, DESCRIPTION_MAX_LENGTH)));
        }
    }

    private void checkUserIdConstraints() {
        if (Objects.isNull(this.task.getUserId())) {
            this.validationHandler().append(new DomainError("'userId' should not be null"));
        }
    }

    private void checkCreatedAtConstraints() {
        if (Objects.isNull(this.task.getCreatedAt())) {
            this.validationHandler().append(new DomainError("'createdAt' should not be null"));
        }
    }

    private void checkUpdatedAtConstraints() {
        if (Objects.isNull(this.task.getUpdatedAt())) {
            this.validationHandler().append(new DomainError("'updatedAt' should not be null"));
        }
    }

}
