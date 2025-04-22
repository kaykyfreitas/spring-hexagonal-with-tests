package dev.kaykyfreitas.task.manager.api.taskmanagerapi.application.usecase.user.retrieve.get;

import dev.kaykyfreitas.task.manager.api.taskmanagerapi.Fixture;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.application.usecase.UseCaseTest;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.exception.NotFoundException;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.user.UserGateway;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.user.UserId;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class GetUserUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultGetUserUseCase useCase;

    @Mock
    private UserGateway userGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(userGateway);
    }

    @Test
    void givenValidCommand_whenCallsGetUser_shouldReturnUser() {
        // Given
        final var expectedUser = Fixture.Users.john();

        final var command = GetUserCommand.with(expectedUser.getId().getValue());

        when(userGateway.findById(any())).thenReturn(Optional.of(expectedUser));

        // When
        final var actualOutput = useCase.execute(command);

        // Then

        assertNotNull(actualOutput);
        assertEquals(expectedUser.getId().getValue(), actualOutput.id());
        assertEquals(expectedUser.getName(), actualOutput.name());
        assertEquals(expectedUser.getUsername(), actualOutput.username());
        assertEquals(expectedUser.getEmail(), actualOutput.email());
        assertEquals(expectedUser.getCreatedAt(), actualOutput.createdAt());
        assertEquals(expectedUser.getUpdatedAt(), actualOutput.updatedAt());

        verify(userGateway, times(1)).findById(any());
    }

    @Test
    void givenInexistentUser_whenCallsGetUser_shouldReceiveNotFoundError() {
        // Given
        final var inexistentUserId = UserId.from("123");

        final var command = GetUserCommand.with(inexistentUserId.getValue());

        final var expectedErrorMessage = "user with id %s was not found".formatted(inexistentUserId.getValue());

        when(userGateway.findById(any())).thenReturn(Optional.empty());

        // When
        final var actualException = assertThrows(
                NotFoundException.class,
                () -> useCase.execute(command)
        );

        // Then
        assertNotNull(actualException);
        assertNotNull(actualException);
        assertEquals(expectedErrorMessage, actualException.getMessage());

        verify(userGateway, times(1)).findById(any());
    }

}
