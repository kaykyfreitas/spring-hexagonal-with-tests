package dev.kaykyfreitas.task.manager.api.taskmanagerapi.adapter.outbound.jpa.repository;

import dev.kaykyfreitas.task.manager.api.taskmanagerapi.adapter.outbound.jpa.entity.UserJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<UserJpaEntity, String> {
}
