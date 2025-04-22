package dev.kaykyfreitas.task.manager.api.taskmanagerapi.adapter.outbound.jpa.repository;

import dev.kaykyfreitas.task.manager.api.taskmanagerapi.Fixture;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.adapter.PostgresGatewayTest;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.adapter.outbound.jpa.entity.UserJpaEntity;
import org.hibernate.PropertyValueException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.jupiter.api.Assertions.*;

@PostgresGatewayTest
class UserJpaRepositoryTest {

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Test
    void givenAValidEntity_whenCallsSave_shouldSaveEntity() {
        assertEquals(0, userJpaRepository.count());

        // Given
        final var user = Fixture.Users.john();

        final var userJpaEntity = UserJpaEntity.fromDomain(user);

        // When
        final var savedUserJpaEntity = userJpaRepository.save(userJpaEntity);

        // Then
        assertNotNull(savedUserJpaEntity);
        assertEquals(savedUserJpaEntity.getId(), userJpaEntity.getId());
        assertEquals(savedUserJpaEntity.getName(), userJpaEntity.getName());
        assertEquals(savedUserJpaEntity.getUsername(), userJpaEntity.getUsername());
        assertEquals(savedUserJpaEntity.getEmail(), userJpaEntity.getEmail());
        assertEquals(savedUserJpaEntity.getCreatedAt(), userJpaEntity.getCreatedAt());
        assertEquals(savedUserJpaEntity.getUpdatedAt(), userJpaEntity.getUpdatedAt());
        assertEquals(1, userJpaRepository.count());
    }

    @Test
    void givenInvalidNullName_whenCallsSave_shouldReturnError() {
        assertEquals(0, userJpaRepository.count());

        // Given
        final var user = Fixture.Users.john();

        final var userJpaEntity = UserJpaEntity.fromDomain(user);
        userJpaEntity.setName(null);

        final var expectedPropertyName = "name";
        final var expectedMessage = "not-null property references a null or transient value: dev.kaykyfreitas.task.manager.api.taskmanagerapi.adapter.outbound.jpa.entity.UserJpaEntity.name";

        // When
        final var actualException = assertThrows(
                DataIntegrityViolationException.class,
                () -> userJpaRepository.save(userJpaEntity)
        );

        // Then
        final var actualCause = assertInstanceOf(PropertyValueException.class, actualException.getCause());

        assertEquals(expectedPropertyName, actualCause.getPropertyName());
        assertEquals(expectedMessage, actualCause.getMessage());
    }

    @Test
    void givenInvalidNullUsername_whenCallsSave_shouldReturnError() {
        assertEquals(0, userJpaRepository.count());

        // Given
        final var user = Fixture.Users.john();

        final var userJpaEntity = UserJpaEntity.fromDomain(user);
        userJpaEntity.setUsername(null);

        final var expectedPropertyName = "username";
        final var expectedMessage = "not-null property references a null or transient value: dev.kaykyfreitas.task.manager.api.taskmanagerapi.adapter.outbound.jpa.entity.UserJpaEntity.username";

        // When
        final var actualException = assertThrows(
                DataIntegrityViolationException.class,
                () -> userJpaRepository.save(userJpaEntity)
        );

        // Then
        final var actualCause = assertInstanceOf(PropertyValueException.class, actualException.getCause());

        assertEquals(expectedPropertyName, actualCause.getPropertyName());
        assertEquals(expectedMessage, actualCause.getMessage());
    }

    @Test
    void givenInvalidNullEmail_whenCallsSave_shouldReturnError() {
        assertEquals(0, userJpaRepository.count());

        // Given
        final var user = Fixture.Users.john();

        final var userJpaEntity = UserJpaEntity.fromDomain(user);
        userJpaEntity.setEmail(null);

        final var expectedPropertyName = "email";
        final var expectedMessage = "not-null property references a null or transient value: dev.kaykyfreitas.task.manager.api.taskmanagerapi.adapter.outbound.jpa.entity.UserJpaEntity.email";

        // When
        final var actualException = assertThrows(
                DataIntegrityViolationException.class,
                () -> userJpaRepository.save(userJpaEntity)
        );

        // Then
        final var actualCause = assertInstanceOf(PropertyValueException.class, actualException.getCause());

        assertEquals(expectedPropertyName, actualCause.getPropertyName());
        assertEquals(expectedMessage, actualCause.getMessage());
    }

    @Test
    void givenInvalidNullCreatedAt_whenCallsSave_shouldReturnError() {
        assertEquals(0, userJpaRepository.count());

        // Given
        final var user = Fixture.Users.john();

        final var userJpaEntity = UserJpaEntity.fromDomain(user);
        userJpaEntity.setCreatedAt(null);

        final var expectedPropertyName = "createdAt";
        final var expectedMessage = "not-null property references a null or transient value: dev.kaykyfreitas.task.manager.api.taskmanagerapi.adapter.outbound.jpa.entity.UserJpaEntity.createdAt";

        // When
        final var actualException = assertThrows(
                DataIntegrityViolationException.class,
                () -> userJpaRepository.save(userJpaEntity)
        );

        // Then
        final var actualCause = assertInstanceOf(PropertyValueException.class, actualException.getCause());

        assertEquals(expectedPropertyName, actualCause.getPropertyName());
        assertEquals(expectedMessage, actualCause.getMessage());
    }

    @Test
    void givenInvalidNullUpdatedAt_whenCallsSave_shouldReturnError() {
        assertEquals(0, userJpaRepository.count());

        // Given
        final var user = Fixture.Users.john();

        final var userJpaEntity = UserJpaEntity.fromDomain(user);
        userJpaEntity.setUpdatedAt(null);

        final var expectedPropertyName = "updatedAt";
        final var expectedMessage = "not-null property references a null or transient value: dev.kaykyfreitas.task.manager.api.taskmanagerapi.adapter.outbound.jpa.entity.UserJpaEntity.updatedAt";

        // When
        final var actualException = assertThrows(
                DataIntegrityViolationException.class,
                () -> userJpaRepository.save(userJpaEntity)
        );

        // Then
        final var actualCause = assertInstanceOf(PropertyValueException.class, actualException.getCause());

        assertEquals(expectedPropertyName, actualCause.getPropertyName());
        assertEquals(expectedMessage, actualCause.getMessage());
    }

}
