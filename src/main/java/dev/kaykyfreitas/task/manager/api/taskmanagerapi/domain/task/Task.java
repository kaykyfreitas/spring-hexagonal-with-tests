package dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.task;

import dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.AggregateRoot;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.user.UserId;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.validation.ValidationHandler;

import java.time.Instant;

public class Task extends AggregateRoot<TaskId> {

    private String title;
    private String description;
    private TaskStatus status;
    private final UserId userId;
    private final Instant createdAt;
    private Instant updatedAt;

    private Task(
            final TaskId id,
            final String title,
            final String description,
            final TaskStatus status,
            final UserId userId,
            final Instant createdAt,
            final Instant updatedAt
    ) {
        super(id);
        this.title = title;
        this.description = description;
        this.status = status;
        this.userId = userId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Task newTask(
            final String title,
            final String description,
            final UserId userId
    ) {
        final var id = TaskId.unique();
        final var now = Instant.now();
        return new Task(id, title, description, TaskStatus.TODO, userId, now, now);
    }

    public static Task with(
            final TaskId id,
            final String title,
            final String description,
            final TaskStatus status,
            final UserId userId,
            final Instant createdAt,
            final Instant updatedAt
    ) {
        return new Task(id, title, description, status, userId, createdAt, updatedAt);
    }

    public static Task with(final Task aTask) {
        return with(
                aTask.id,
                aTask.title,
                aTask.description,
                aTask.status,
                aTask.userId,
                aTask.createdAt,
                aTask.updatedAt
        );
    }

    @Override
    public void validate(ValidationHandler handler) {
        new TaskValidator(this, handler).validate();
    }

    public Task update(final String title, final String description) {
        this.title = title;
        this.description = description;
        this.updatedAt = Instant.now();
        return this;
    }

    public Task updateStatus(final TaskStatus status) {
        this.status = status;
        this.updatedAt = Instant.now();
        return this;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public UserId getUserId() {
        return userId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

}
