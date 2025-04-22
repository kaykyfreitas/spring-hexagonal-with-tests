package dev.kaykyfreitas.task.manager.api.taskmanagerapi.adapter.outbound.jpa;

import dev.kaykyfreitas.task.manager.api.taskmanagerapi.adapter.outbound.jpa.entity.TaskJpaEntity;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.adapter.outbound.jpa.repository.TaskJpaRepository;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.task.Task;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.task.TaskGateway;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.task.TaskId;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.user.UserId;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TaskPostgresGateway implements TaskGateway {

    private final TaskJpaRepository taskJpaRepository;

    public TaskPostgresGateway(TaskJpaRepository taskJpaRepository) {
        this.taskJpaRepository = taskJpaRepository;
    }

    @Override
    public Task create(final Task task) {
        return this.save(task);
    }

    @Override
    public Task update(final Task task) {
        return this.save(task);
    }

    @Override
    public void deleteById(final TaskId taskId) {
        this.taskJpaRepository.deleteById(taskId.getValue());
    }

    @Override
    public Optional<Task> findById(final TaskId taskId) {
        return this.taskJpaRepository.findById(taskId.getValue())
                .map(TaskJpaEntity::toDomain);
    }

    @Override
    public List<Task> findByUserId(final UserId userId) {
        return this.taskJpaRepository.findAllByUserId(userId.getValue())
                .stream()
                .map(TaskJpaEntity::toDomain)
                .toList();
    }

    private Task save(final Task task) {
        return this.taskJpaRepository.save(TaskJpaEntity.fromDomain(task)).toDomain();
    }
}
