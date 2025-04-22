package dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.task;

import dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.Identifier;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.utils.IdUtils;

import java.util.Objects;

public class TaskId extends Identifier {

    private final String value;

    private TaskId(final String value) {
        this.value = Objects.requireNonNull(value);
    }

    public static TaskId unique() {
        return TaskId.from(IdUtils.uuid());
    }

    public static TaskId from(final String anId) {
        return new TaskId(anId);
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        final TaskId taskId = (TaskId) o;
        return Objects.equals(getValue(), taskId.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getValue());
    }

}
