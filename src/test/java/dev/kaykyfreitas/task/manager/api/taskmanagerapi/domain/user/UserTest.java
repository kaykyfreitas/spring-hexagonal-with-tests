package dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.user;

import dev.kaykyfreitas.task.manager.api.taskmanagerapi.Fixture;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.exception.NotificationException;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.utils.InstantUtils;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void givenValidPrams_whenClassNewUser_thenInstantiateUser() {
        // Given
        final var expectedName = "John Doe";
        final var expectedUsername = "john.doe";
        final var expectedEmail = "john.doe@email.com";

        // When
        final var actualUser = User.newUser(expectedName, expectedUsername, expectedEmail);

        // Then
        assertNotNull(actualUser);
        assertNotNull(actualUser.getId());
        assertEquals(expectedName, actualUser.getName());
        assertEquals(expectedUsername, actualUser.getUsername());
        assertEquals(expectedEmail, actualUser.getEmail());
        assertNotNull(actualUser.getCreatedAt());
        assertNotNull(actualUser.getUpdatedAt());
        assertEquals(actualUser.getCreatedAt(), actualUser.getUpdatedAt());
    }

    @Test
    void givenInvalidNullName_whenCallsNewUser_shouldReceiveError() {
        // Given
        final String expectedName = null;
        final var expectedUsername = "john.doe";
        final var expectedEmail = "john.doe@email.com";

        final var expectedErrorMessage = "'name' should not be null";
        final var expectedErrorCount = 1;

        // When
        final var actualException = assertThrows(
                NotificationException.class,
                () -> User.newUser(expectedName, expectedUsername, expectedEmail)
        );

        // Then
        assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
        assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    void givenInvalidEmptyName_whenCallsNewUser_shouldReceiveError() {
        // Given
        final var expectedName = "";
        final var expectedUsername = "john.doe";
        final var expectedEmail = "john.doe@email.com";

        final var expectedErrorMessage = "'name' should not be empty";
        final var expectedErrorCount = 1;

        // When
        final var actualException = assertThrows(
                NotificationException.class,
                () -> User.newUser(expectedName, expectedUsername, expectedEmail)
        );

        // Then
        assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
        assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    void givenInvalidBiggestName_whenCallsNewUser_shouldReceiveError() {
        // Given
        final var expectedName = Fixture.text(110);
        final var expectedUsername = "john.doe";
        final var expectedEmail = "john.doe@email.com";

        final var expectedErrorMessage = "'name' must be between 3 and 100 characters";
        final var expectedErrorCount = 1;

        // When
        final var actualException = assertThrows(
                NotificationException.class,
                () -> User.newUser(expectedName, expectedUsername, expectedEmail)
        );

        // Then
        assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
        assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    void givenInvalidSmallestName_whenCallsNewUser_shouldReceiveError() {
        // Given
        final var expectedName = Fixture.text(2);
        final var expectedUsername = "john.doe";
        final var expectedEmail = "john.doe@email.com";

        final var expectedErrorMessage = "'name' must be between 3 and 100 characters";
        final var expectedErrorCount = 1;

        // When
        final var actualException = assertThrows(
                NotificationException.class,
                () -> User.newUser(expectedName, expectedUsername, expectedEmail)
        );

        // Then
        assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
        assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    void givenInvalidNullUsername_whenCallsNewUser_shouldReceiveError() {
        // Given
        final var expectedName = "John Doe";
        final String expectedUsername = null;
        final var expectedEmail = "john.doe@email.com";

        final var expectedErrorMessage = "'username' should not be null";
        final var expectedErrorCount = 1;

        // When
        final var actualException = assertThrows(
                NotificationException.class,
                () -> User.newUser(expectedName, expectedUsername, expectedEmail)
        );

        // Then
        assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
        assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    void givenInvalidEmptyUsername_whenCallsNewUser_shouldReceiveError() {
        // Given
        final var expectedName = "John Doe";
        final var expectedUsername = "";
        final var expectedEmail = "john.doe@email.com";

        final var expectedErrorMessage = "'username' should not be empty";
        final var expectedErrorCount = 1;

        // When
        final var actualException = assertThrows(
                NotificationException.class,
                () -> User.newUser(expectedName, expectedUsername, expectedEmail)
        );

        // Then
        assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
        assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    void givenInvalidBiggestUsername_whenCallsNewUser_shouldReceiveError() {
        // Given
        final var expectedName = "John Doe";
        final var expectedUsername = Fixture.text(20);
        final var expectedEmail = "john.doe@email.com";

        final var expectedErrorMessage = "'username' must be between 3 and 15 characters";
        final var expectedErrorCount = 1;

        // When
        final var actualException = assertThrows(
                NotificationException.class,
                () -> User.newUser(expectedName, expectedUsername, expectedEmail)
        );

        // Then
        assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
        assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    void givenInvalidSmallestUsername_whenCallsNewUser_shouldReceiveError() {
        // Given
        final var expectedName = "John Doe";
        final var expectedUsername = Fixture.text(2);
        final var expectedEmail = "john.doe@email.com";

        final var expectedErrorMessage = "'username' must be between 3 and 15 characters";
        final var expectedErrorCount = 1;

        // When
        final var actualException = assertThrows(
                NotificationException.class,
                () -> User.newUser(expectedName, expectedUsername, expectedEmail)
        );

        // Then
        assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
        assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    void givenInvalidNullEmail_whenCallsNewUser_shouldReceiveError() {
        // Given
        final var expectedName = "John Doe";
        final var expectedUsername = "john.doe";
        final String expectedEmail = null;

        final var expectedErrorMessage = "'email' should not be null";
        final var expectedErrorCount = 1;

        // Then
        final var actualException = assertThrows(
                NotificationException.class,
                () -> User.newUser(expectedName, expectedUsername, expectedEmail)
        );

        // Then
        assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
        assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    void givenInvalidEmptyEmail_whenCallsNewUser_shouldReceiveError() {
        // Given
        final var expectedName = "John Doe";
        final var expectedUsername = "john.doe";
        final var expectedEmail = "";

        final var expectedErrorMessage = "'email' should not be empty";
        final var expectedErrorCount = 1;

        // When
        final var actualException = assertThrows(
                NotificationException.class,
                () -> User.newUser(expectedName, expectedUsername, expectedEmail)
        );

        // Then
        assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
        assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    void givenInvalidMalformedEmail_whenCallsNewUser_shouldReceiveError() {
        // Given
        final var expectedName = "John Doe";
        final var expectedUsername = "john.doe";
        final var expectedEmail = "john.doe.com";

        final var expectedErrorMessage = "'email' format is not valid";
        final var expectedErrorCount = 1;

        // When
        final var actualException = assertThrows(
                NotificationException.class,
                () -> User.newUser(expectedName, expectedUsername, expectedEmail)
        );

        // Then
        assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
        assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    void givenInvalidNullCreatedAt_whenClassWith_shouldReceiveError() {
        // Given
        final var userId = UserId.unique();
        final var expectedName = "John Doe";
        final var expectedUsername = "john.doe";
        final var expectedEmail = "johndoe@email.com";
        final Instant createdAt = null;
        final var updatedAt = InstantUtils.now();

        final var expectedErrorMessage = "'createdAt' should not be null";
        final var expectedErrorCount = 1;

        // When
        final var actualException = assertThrows(
                NotificationException.class,
                () -> User.with(userId, expectedName, expectedUsername, expectedEmail, createdAt, updatedAt)
        );

        // Then
        assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
        assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    void givenInvalidNullUpdatedAt_whenClassWith_shouldReceiveError() {
        // Given
        final var userId = UserId.unique();
        final var expectedName = "John Doe";
        final var expectedUsername = "john.doe";
        final var expectedEmail = "johndoe@email.com";
        final var createdAt = InstantUtils.now();
        final Instant updatedAt = null;

        final var expectedErrorMessage = "'updatedAt' should not be null";
        final var expectedErrorCount = 1;

        // When
        final var actualException = assertThrows(
                NotificationException.class,
                () -> User.with(userId, expectedName, expectedUsername, expectedEmail, createdAt, updatedAt)
        );

        // Then
        assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
        assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    void givenValidParams_whenCallsUpdate_thenUpdateUser() {
        // Given
        final var expectedName = "John Doe";
        final var expectedUsername = "john.doe";
        final var expectedEmail = "john.doe@email.com";

        final var actualUser = User.newUser(expectedName, expectedUsername, expectedEmail);

        assertNotNull(actualUser);
        assertNotNull(actualUser.getId());
        assertEquals(expectedName, actualUser.getName());
        assertEquals(expectedUsername, actualUser.getUsername());
        assertEquals(expectedEmail, actualUser.getEmail());
        assertNotNull(actualUser.getCreatedAt());
        assertNotNull(actualUser.getUpdatedAt());
        assertEquals(actualUser.getCreatedAt(), actualUser.getUpdatedAt());

        final var expectedUpdatedName = "John Doe Updated";
        final var expectedUpdatedEmail = "johndoeupdated@email.com";

        // When
        final var actualUpdatedUser = actualUser.update(expectedUpdatedName, expectedUpdatedEmail);

        // Then
        assertNotNull(actualUser);
        assertNotNull(actualUser.getId());
        assertEquals(expectedUpdatedName, actualUser.getName());
        assertEquals(expectedUsername, actualUser.getUsername());
        assertEquals(expectedUpdatedEmail, actualUser.getEmail());
        assertNotNull(actualUser.getCreatedAt());
        assertNotNull(actualUser.getUpdatedAt());
        assertTrue(actualUpdatedUser.getCreatedAt().isBefore(actualUpdatedUser.getUpdatedAt()));

        assertNotNull(actualUpdatedUser);
        assertNotNull(actualUpdatedUser.getId());
        assertEquals(expectedUpdatedName, actualUpdatedUser.getName());
        assertEquals(expectedUsername, actualUpdatedUser.getUsername());
        assertEquals(expectedUpdatedEmail, actualUpdatedUser.getEmail());
        assertNotNull(actualUpdatedUser.getCreatedAt());
        assertNotNull(actualUpdatedUser.getUpdatedAt());
        assertTrue(actualUpdatedUser.getCreatedAt().isBefore(actualUpdatedUser.getUpdatedAt()));
    }

}