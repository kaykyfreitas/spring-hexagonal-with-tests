package dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.task;

import dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.user.User;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.user.UserId;

import java.util.List;
import java.util.Optional;

public interface TaskGateway {
    Task create(Task task);
    Task update(Task task);
    void deleteById(TaskId taskId);
    Optional<Task> findById(TaskId taskId);
    List<Task> findByUserId(UserId userId);
}
