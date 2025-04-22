package dev.kaykyfreitas.task.manager.api.taskmanagerapi.application.usecase.user.retrieve.get;

import dev.kaykyfreitas.task.manager.api.taskmanagerapi.Fixture;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.adapter.outbound.jpa.entity.UserJpaEntity;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.adapter.outbound.jpa.repository.UserJpaRepository;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.application.IntegrationTest;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.exception.NotFoundException;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.user.UserGateway;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.user.UserId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@IntegrationTest
class GetUserUseCaseIntegrationTest {

    @Autowired
    private GetUserUseCase useCase;

    @Autowired
    private UserJpaRepository userJpaRepository;

    @MockitoSpyBean
    private UserGateway userGateway;

    @Test
    void givenValidCommand_whenCallsGetUser_shouldReturnUser() {
        // Given
        assertEquals(0, userJpaRepository.count());

        final var expectedUser = Fixture.Users.john();

        userJpaRepository.save(UserJpaEntity.fromDomain(expectedUser));

        assertEquals(1, userJpaRepository.count());

        final var command = GetUserCommand.with(expectedUser.getId().getValue());

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
        assertEquals(0, userJpaRepository.count());

        final var inexistentUserId = UserId.from("123");

        final var command = GetUserCommand.with(inexistentUserId.getValue());

        final var expectedErrorMessage = "user with id %s was not found".formatted(inexistentUserId.getValue());

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
