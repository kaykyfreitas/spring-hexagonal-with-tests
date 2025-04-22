package dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.user;

import dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.Identifier;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.utils.IdUtils;

import java.util.Objects;

public class UserId extends Identifier {

    private final String value;

    private UserId(final String value) {
        this.value = Objects.requireNonNull(value);
    }

    public static UserId unique() {
        return UserId.from(IdUtils.uuid());
    }

    public static UserId from(final String anId) {
        return new UserId(anId);
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        final UserId userId = (UserId) o;
        return Objects.equals(getValue(), userId.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getValue());
    }

}
