package dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.task;

import dev.kaykyfreitas.task.manager.api.taskmanagerapi.Fixture;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.exception.DomainException;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.user.UserId;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.utils.InstantUtils;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.validation.handler.ThrowsValidationHandler;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    @Test
    void givenValidPrams_whenClassNewTask_thenInstantiateTask() {
        // Given
        final var expectedUser = Fixture.Users.john();
        final var expectedTitle = "title";
        final var expectedDescription = "description";

        // When
        final var task = Task.newTask(expectedTitle, expectedDescription, expectedUser.getId());

        // Then
        assertNotNull(task);
        assertNotNull(task.getId());
        assertEquals(expectedTitle, task.getTitle());
        assertEquals(expectedDescription, task.getDescription());
        assertEquals(TaskStatus.TODO, task.getStatus());
        assertEquals(expectedUser.getId(), task.getUserId());
        assertNotNull(task.getCreatedAt());
        assertNotNull(task.getUpdatedAt());
        assertEquals(task.getCreatedAt(), task.getUpdatedAt());
    }

    @Test
    void givenInvalidNullTitle_whenClassNewTaskAndValidate_shouldReceiveError() {
        // Given
        final var expectedUser = Fixture.Users.john();
        final String expectedTitle = null;
        final var expectedDescription = "description";

        final var actualTask = Task.newTask(expectedTitle, expectedDescription, expectedUser.getId());

        final var expectedErrorMessage = "'title' should not be null";
        final var expectedErrorCount = 1;

        // When
        final var actualException = assertThrows(
                DomainException.class,
                () -> actualTask.validate(new ThrowsValidationHandler())
        );

        // Then
        assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
        assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    void givenInvalidEmptyTitle_whenClassNewTaskAndValidate_shouldReceiveError() {
        // Given
        final var expectedUser = Fixture.Users.john();
        final var expectedTitle = "";
        final var expectedDescription = "description";

        final var actualTask = Task.newTask(expectedTitle, expectedDescription, expectedUser.getId());

        final var expectedErrorMessage = "'title' should not be empty";
        final var expectedErrorCount = 1;

        // When
        final var actualException = assertThrows(
                DomainException.class,
                () -> actualTask.validate(new ThrowsValidationHandler())
        );

        // Then
        assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
        assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    void givenInvalidBiggestTitle_whenClassNewTaskAndValidate_shouldReceiveError() {
        // Given
        final var expectedUser = Fixture.Users.john();
        final var expectedTitle = Fixture.text(60);
        final var expectedDescription = "description";

        final var actualTask = Task.newTask(expectedTitle, expectedDescription, expectedUser.getId());

        final var expectedErrorMessage = "'title' must be between 2 and 50 characters";
        final var expectedErrorCount = 1;

        // When
        final var actualException = assertThrows(
                DomainException.class,
                () -> actualTask.validate(new ThrowsValidationHandler())
        );

        // Then
        assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
        assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    void givenInvalidSmallestTitle_whenClassNewTaskAndValidate_shouldReceiveError() {
        // Given
        final var expectedUser = Fixture.Users.john();
        final var expectedTitle = Fixture.text(1);
        final var expectedDescription = "description";

        final var actualTask = Task.newTask(expectedTitle, expectedDescription, expectedUser.getId());

        final var expectedErrorMessage = "'title' must be between 2 and 50 characters";
        final var expectedErrorCount = 1;

        // When
        final var actualException = assertThrows(
                DomainException.class,
                () -> actualTask.validate(new ThrowsValidationHandler())
        );

        // Then
        assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
        assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    void givenInvalidNullDescription_whenClassNewTaskAndValidate_shouldReceiveError() {
        // Given
        final var expectedUser = Fixture.Users.john();
        final var expectedTitle = "title";
        final String expectedDescription = null;

        final var actualTask = Task.newTask(expectedTitle, expectedDescription, expectedUser.getId());

        final var expectedErrorMessage = "'description' should not be null";
        final var expectedErrorCount = 1;

        // When
        final var actualException = assertThrows(
                DomainException.class,
                () -> actualTask.validate(new ThrowsValidationHandler())
        );

        // Then
        assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
        assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    void givenInvalidNullEmpty_whenClassNewTaskAndValidate_shouldReceiveError() {
        // Given
        final var expectedUser = Fixture.Users.john();
        final var expectedTitle = "title";
        final var expectedDescription = "";

        final var actualTask = Task.newTask(expectedTitle, expectedDescription, expectedUser.getId());

        final var expectedErrorMessage = "'description' should not be empty";
        final var expectedErrorCount = 1;

        // When
        final var actualException = assertThrows(
                DomainException.class,
                () -> actualTask.validate(new ThrowsValidationHandler())
        );

        // Then
        assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
        assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    void givenInvalidBiggestEmpty_whenClassNewTaskAndValidate_shouldReceiveError() {
        // Given
        final var expectedUser = Fixture.Users.john();
        final var expectedTitle = "title";
        final var expectedDescription = Fixture.text(510);

        final var actualTask = Task.newTask(expectedTitle, expectedDescription, expectedUser.getId());

        final var expectedErrorMessage = "'description' must be between 2 and 500 characters";
        final var expectedErrorCount = 1;

        // When
        final var actualException = assertThrows(
                DomainException.class,
                () -> actualTask.validate(new ThrowsValidationHandler())
        );

        // Then
        assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
        assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    void givenInvalidSmallestEmpty_whenClassNewTaskAndValidate_shouldReceiveError() {
        // Given
        final var expectedUser = Fixture.Users.john();
        final var expectedTitle = "title";
        final var expectedDescription = Fixture.text(1);

        final var actualTask = Task.newTask(expectedTitle, expectedDescription, expectedUser.getId());

        final var expectedErrorMessage = "'description' must be between 2 and 500 characters";
        final var expectedErrorCount = 1;

        // When
        final var actualException = assertThrows(
                DomainException.class,
                () -> actualTask.validate(new ThrowsValidationHandler())
        );

        // Then
        assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
        assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    void givenInvalidNullUserId_whenClassNewTaskAndValidate_shouldReceiveError() {
        // Given
        final UserId expectedUserId = null;
        final var expectedTitle = "title";
        final var expectedDescription = "description";

        final var actualTask = Task.newTask(expectedTitle, expectedDescription, expectedUserId);

        final var expectedErrorMessage = "'userId' should not be null";
        final var expectedErrorCount = 1;

        // When
        final var actualException = assertThrows(
                DomainException.class,
                () -> actualTask.validate(new ThrowsValidationHandler())
        );

        // Then
        assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
        assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    void givenInvalidNullCreatedAt_whenClassNewTaskAndValidate_shouldReceiveError() {
        // Given
        final var expectedUser = Fixture.Users.john();
        final var taskId = TaskId.unique();
        final var expectedTitle = "title";
        final var expectedDescription = "description";
        final var expectedStaus = TaskStatus.TODO;
        final Instant expectedCreatedAt = null;
        final var expectedUpdatedAt = InstantUtils.now();

        final var actualTask = Task.with(
                taskId,
                expectedTitle,
                expectedDescription,
                expectedStaus,
                expectedUser.getId(),
                expectedCreatedAt,
                expectedUpdatedAt
        );

        final var expectedErrorMessage = "'createdAt' should not be null";
        final var expectedErrorCount = 1;

        // When
        final var actualException = assertThrows(
                DomainException.class,
                () -> actualTask.validate(new ThrowsValidationHandler())
        );

        // Then
        assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
        assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    void givenInvalidNullUpdatedAt_whenClassNewTaskAndValidate_shouldReceiveError() {
        // Given
        final var expectedUser = Fixture.Users.john();
        final var taskId = TaskId.unique();
        final var expectedTitle = "title";
        final var expectedDescription = "description";
        final var expectedStaus = TaskStatus.TODO;
        final var expectedCreatedAt = InstantUtils.now();
        final Instant expectedUpdatedAt = null;

        final var actualTask = Task.with(
                taskId,
                expectedTitle,
                expectedDescription,
                expectedStaus,
                expectedUser.getId(),
                expectedCreatedAt,
                expectedUpdatedAt
        );

        final var expectedErrorMessage = "'updatedAt' should not be null";
        final var expectedErrorCount = 1;

        // When
        final var actualException = assertThrows(
                DomainException.class,
                () -> actualTask.validate(new ThrowsValidationHandler())
        );

        // Then
        assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
        assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    void givenValidPrams_whenClassUpdate_thenInstantiateTask() {
        // Given
        final var expectedUser = Fixture.Users.john();
        final var expectedTitle = "title";
        final var expectedDescription = "description";

        final var task = Task.newTask(expectedTitle, expectedDescription, expectedUser.getId());

        assertNotNull(task);
        assertNotNull(task.getId());
        assertEquals(expectedTitle, task.getTitle());
        assertEquals(expectedDescription, task.getDescription());
        assertEquals(TaskStatus.TODO, task.getStatus());
        assertEquals(expectedUser.getId(), task.getUserId());
        assertNotNull(task.getCreatedAt());
        assertNotNull(task.getUpdatedAt());
        assertEquals(task.getCreatedAt(), task.getUpdatedAt());

        final var expectedUpdatedTitle = "title updated";
        final var expectedUpdatedDescription = "description updated";

        // When
        final var updatedTask = task.update(expectedUpdatedTitle, expectedUpdatedDescription);

        // Then
        assertNotNull(task);
        assertNotNull(task.getId());
        assertEquals(expectedUpdatedTitle, task.getTitle());
        assertEquals(expectedUpdatedDescription, task.getDescription());
        assertEquals(TaskStatus.TODO, task.getStatus());
        assertEquals(expectedUser.getId(), task.getUserId());
        assertNotNull(task.getCreatedAt());
        assertNotNull(task.getUpdatedAt());
        assertTrue(task.getCreatedAt().isBefore(task.getUpdatedAt()));

        assertNotNull(updatedTask);
        assertNotNull(updatedTask.getId());
        assertEquals(expectedUpdatedTitle, updatedTask.getTitle());
        assertEquals(expectedUpdatedDescription, updatedTask.getDescription());
        assertEquals(TaskStatus.TODO, updatedTask.getStatus());
        assertEquals(expectedUser.getId(), updatedTask.getUserId());
        assertNotNull(updatedTask.getCreatedAt());
        assertNotNull(updatedTask.getUpdatedAt());
        assertTrue(updatedTask.getCreatedAt().isBefore(updatedTask.getUpdatedAt()));
    }

    @Test
    void givenValidPrams_whenClassUpdateStatus_thenInstantiateTask() {
        // Given
        final var expectedUser = Fixture.Users.john();
        final var expectedTitle = "title";
        final var expectedDescription = "description";

        final var task = Task.newTask(expectedTitle, expectedDescription, expectedUser.getId());

        assertNotNull(task);
        assertNotNull(task.getId());
        assertEquals(expectedTitle, task.getTitle());
        assertEquals(expectedDescription, task.getDescription());
        assertEquals(TaskStatus.TODO, task.getStatus());
        assertEquals(expectedUser.getId(), task.getUserId());
        assertNotNull(task.getCreatedAt());
        assertNotNull(task.getUpdatedAt());
        assertEquals(task.getCreatedAt(), task.getUpdatedAt());

        final var expectedUpdatedStatus = TaskStatus.DOING;

        // When
        final var updatedTask = task.updateStatus(expectedUpdatedStatus);

        // Then
        assertNotNull(task);
        assertNotNull(task.getId());
        assertEquals(expectedTitle, task.getTitle());
        assertEquals(expectedDescription, task.getDescription());
        assertEquals(expectedUpdatedStatus, task.getStatus());
        assertEquals(expectedUser.getId(), task.getUserId());
        assertNotNull(task.getCreatedAt());
        assertNotNull(task.getUpdatedAt());
        assertTrue(task.getCreatedAt().isBefore(task.getUpdatedAt()));

        assertNotNull(updatedTask);
        assertNotNull(updatedTask.getId());
        assertEquals(expectedTitle, updatedTask.getTitle());
        assertEquals(expectedDescription, updatedTask.getDescription());
        assertEquals(expectedUpdatedStatus, updatedTask.getStatus());
        assertEquals(expectedUser.getId(), updatedTask.getUserId());
        assertNotNull(updatedTask.getCreatedAt());
        assertNotNull(updatedTask.getUpdatedAt());
        assertTrue(updatedTask.getCreatedAt().isBefore(updatedTask.getUpdatedAt()));
    }

}