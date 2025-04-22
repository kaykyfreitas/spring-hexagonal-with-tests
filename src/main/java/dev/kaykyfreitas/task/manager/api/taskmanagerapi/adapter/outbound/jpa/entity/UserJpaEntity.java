package dev.kaykyfreitas.task.manager.api.taskmanagerapi.adapter.outbound.jpa.entity;

import dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.user.User;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.user.UserId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
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
@Table(name = "tb_user")
public class UserJpaEntity {

    @Id
    @Column(name = "user_id", nullable = false)
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP(6)")
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false, columnDefinition = "TIMESTAMP(6)")
    private Instant updatedAt;

    public static UserJpaEntity fromDomain(final User user) {
        return new UserJpaEntity(
                user.getId().getValue(),
                user.getName(),
                user.getUsername(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }

    public User toDomain() {
        return User.with(
                UserId.from(this.getId()),
                this.getName(),
                this.getUsername(),
                this.getEmail(),
                this.getCreatedAt(),
                this.getUpdatedAt()
        );
    }

}
