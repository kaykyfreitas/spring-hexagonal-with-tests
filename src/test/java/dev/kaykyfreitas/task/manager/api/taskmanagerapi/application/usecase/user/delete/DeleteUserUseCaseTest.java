package dev.kaykyfreitas.task.manager.api.taskmanagerapi.application.usecase.user.delete;

import dev.kaykyfreitas.task.manager.api.taskmanagerapi.application.usecase.UseCaseTest;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.user.UserGateway;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;

class DeleteUserUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultDeleteUserUseCase useCase;

    @Mock
    private UserGateway userGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(userGateway);
    }

    @Test
    void givenValidCommand_whenCallsDeleteUser_shouldDeleteUser() {
        // Given
        final var expectedId = "123";

        final var aCommand = DeleteUserCommand.with(expectedId);

        Mockito.doNothing().when(userGateway).deleteById(Mockito.any());

        // When
        useCase.execute(aCommand);

        // Then
        Mockito.verify(userGateway, Mockito.times(1)).deleteById(Mockito.any());
    }

}
