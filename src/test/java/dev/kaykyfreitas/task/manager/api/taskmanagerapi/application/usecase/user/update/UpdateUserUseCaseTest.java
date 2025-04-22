package dev.kaykyfreitas.task.manager.api.taskmanagerapi.application.usecase.user.update;

import dev.kaykyfreitas.task.manager.api.taskmanagerapi.Fixture;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.application.usecase.UseCaseTest;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.exception.NotFoundException;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.exception.NotificationException;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.user.User;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.user.UserGateway;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class UpdateUserUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultUpdateUserUseCase useCase;

    @Mock
    private UserGateway userGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(userGateway);
    }

    @Test
    void givenValidParams_whenCallUpdateUser_shouldReturnUser() {
        // Given
        final var expectedName = Fixture.Users.name();
        final var expectedUsername = Fixture.Users.username();
        final var expectedEmail = Fixture.Users.email();

        final var user = User.newUser(expectedName, expectedUsername, expectedEmail);

        final var expectedUpdateName = "updated name";
        final var expectedUpdateEmail = "updated@email.com";

        final var command = UpdateUserCommand.with(
                user.getId().getValue(),
                expectedUpdateName,
                expectedUpdateEmail
        );

        when(userGateway.findById(user.getId())).thenReturn(Optional.of(user));
        when(userGateway.update(any())).thenAnswer(returnsFirstArg());

        // When
        final var actualOutput = useCase.execute(command);

        // Then
        assertNotNull(actualOutput);
        assertNotNull(actualOutput.id());

        Mockito.verify(userGateway, Mockito.times(1)).findById(Mockito.any());
        Mockito.verify(userGateway, Mockito.times(1)).update(Mockito.any());
    }

    @Test
    void givenInexistentUser_whenCallUpdateUser_shouldReceiveNotFoundError() {
        // Given
        final var expectedId = "123-456-789";
        final var expectedUpdateName = "updated name";
        final var expectedUpdateEmail = "updated@email.com";

        final var expectedErrorMessage = "user with id %s was not found".formatted(expectedId);

        final var command = UpdateUserCommand.with(
                expectedId,
                expectedUpdateName,
                expectedUpdateEmail
        );

        when(userGateway.findById(any())).thenReturn(Optional.empty());

        // When
        final var actualException = assertThrows(
                NotFoundException.class,
                () -> useCase.execute(command)
        );

        // Then
        assertNotNull(actualException);
        assertEquals(expectedErrorMessage, actualException.getMessage());

        Mockito.verify(userGateway, Mockito.times(1)).findById(Mockito.any());
        Mockito.verify(userGateway, Mockito.times(0)).update(Mockito.any());
    }

    @Test
    void givenInvalidNullName_whenCallUpdateUser_shouldReceiveError() {
        // Given
        final var expectedName = Fixture.Users.name();
        final var expectedUsername = Fixture.Users.username();
        final var expectedEmail = Fixture.Users.email();

        final var user = User.newUser(expectedName, expectedUsername, expectedEmail);

        final String expectedUpdateName = null;
        final var expectedUpdateEmail = "updated@email.com";

        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' should not be null";

        final var command = UpdateUserCommand.with(
                user.getId().getValue(),
                expectedUpdateName,
                expectedUpdateEmail
        );

        when(userGateway.findById(user.getId())).thenReturn(Optional.of(user));

        // When
        final var actualException = assertThrows(
                NotificationException.class,
                () -> useCase.execute(command)
        );

        // Then
        assertNotNull(actualException);
        assertEquals(expectedErrorCount, actualException.getErrors().size());
        assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());

        Mockito.verify(userGateway, Mockito.times(1)).findById(Mockito.any());
        Mockito.verify(userGateway, Mockito.times(0)).update(Mockito.any());
    }

}
