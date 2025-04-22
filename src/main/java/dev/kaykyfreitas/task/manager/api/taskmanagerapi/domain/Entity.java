package dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain;

import dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.validation.ValidationHandler;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

public abstract class Entity<ID extends Identifier> {

    protected final ID id;

    protected Entity(final ID id) {
        this.id = requireNonNull(id, "'id' should not be null");
    }

    public abstract void validate(ValidationHandler handler);

    public ID getId() {
        return id;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        final Entity<?> entity = (Entity<?>) o;
        return Objects.equals(getId(), entity.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

}
