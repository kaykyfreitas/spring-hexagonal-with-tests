package dev.kaykyfreitas.task.manager.api.taskmanagerapi.adapter.outbound.jpa.repository;

import dev.kaykyfreitas.task.manager.api.taskmanagerapi.adapter.outbound.jpa.entity.TaskJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskJpaRepository extends JpaRepository<TaskJpaEntity, String> {
    List<TaskJpaEntity> findAllByUserId(String userId);
}
