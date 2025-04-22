package dev.kaykyfreitas.task.manager.api.taskmanagerapi.adapter.outbound.jpa.entity;

import dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.task.Task;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.task.TaskId;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.task.TaskStatus;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.user.UserId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "task")
public class TaskJpaEntity {

    @Id
    private String id;

    private String title;

    private String description;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    private String userId;

    private Instant createdAt;

    private Instant updatedAt;

    public static TaskJpaEntity fromDomain(final Task task) {
        return new TaskJpaEntity(
                task.getId().getValue(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getUserId().getValue(),
                task.getCreatedAt(),
                task.getUpdatedAt()
        );
    }

    public Task toDomain() {
        return Task.with(
                TaskId.from(this.getId()),
                this.getTitle(),
                this.getDescription(),
                this.getStatus(),
                UserId.from(this.getUserId()),
                this.getCreatedAt(),
                this.getUpdatedAt()
        );
    }

}
