package dev.kaykyfreitas.task.manager.api.taskmanagerapi.application.usecase.user.create;

import dev.kaykyfreitas.task.manager.api.taskmanagerapi.Fixture;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.application.usecase.UseCaseTest;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.exception.NotificationException;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.user.UserGateway;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CreateUserUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultCreateUserUseCase useCase;

    @Mock
    private UserGateway userGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(userGateway);
    }

    @Test
    void givenValidCommand_whenCallCreateUser_shouldReturnUser() {
        // Given
        final var expectedName = Fixture.Users.name();
        final var expectedUsername = Fixture.Users.username();
        final var expectedEmail = Fixture.Users.email();

        final var aCommand = CreateUserCommand.with(expectedName, expectedUsername, expectedEmail);

        Mockito.when(userGateway.create(Mockito.any()))
                .thenAnswer(AdditionalAnswers.returnsFirstArg());

        // When
        final var actualOutput = useCase.execute(aCommand);

        // Then
        assertNotNull(actualOutput);
        assertNotNull(actualOutput.id());

        Mockito.verify(userGateway, Mockito.times(1)).create(Mockito.any());
    }

    @Test
    void givenInvalidNullName_whenCallCreateUser_shouldReturnUser() {
        // Given
        final String expectedName = null;
        final var expectedUsername = Fixture.Users.username();
        final var expectedEmail = Fixture.Users.email();

        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' should not be null";

        final var aCommand = CreateUserCommand.with(expectedName, expectedUsername, expectedEmail);

        // When
        final var actualException = assertThrows(
                NotificationException.class,
                () -> useCase.execute(aCommand)
        );

        // Then
        assertNotNull(actualException);
        assertEquals(expectedErrorCount, actualException.getErrors().size());
        assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());

        Mockito.verify(userGateway, Mockito.times(0)).create(Mockito.any());
    }

    @Test
    void givenInvalidNullUsername_whenCallCreateUser_shouldReturnUser() {
        // Given
        final var expectedName = Fixture.Users.name();
        final String expectedUsername = null;
        final var expectedEmail = Fixture.Users.email();

        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'username' should not be null";

        final var aCommand = CreateUserCommand.with(expectedName, expectedUsername, expectedEmail);

        // When
        final var actualException = assertThrows(
                NotificationException.class,
                () -> useCase.execute(aCommand)
        );

        // Then
        assertNotNull(actualException);
        assertEquals(expectedErrorCount, actualException.getErrors().size());
        assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());

        Mockito.verify(userGateway, Mockito.times(0)).create(Mockito.any());
    }

    @Test
    void givenInvalidMalformedEmail_whenCallCreateUser_shouldReturnUser() {
        // Given
        final var expectedName = Fixture.Users.name();
        final var expectedUsername = Fixture.Users.username();
        final var expectedEmail = "invalid-email";

        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'email' format is not valid";

        final var aCommand = CreateUserCommand.with(expectedName, expectedUsername, expectedEmail);

        // When
        final var actualException = assertThrows(
                NotificationException.class,
                () -> useCase.execute(aCommand)
        );

        // Then
        assertNotNull(actualException);
        assertEquals(expectedErrorCount, actualException.getErrors().size());
        assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());

        Mockito.verify(userGateway, Mockito.times(0)).create(Mockito.any());
    }

}