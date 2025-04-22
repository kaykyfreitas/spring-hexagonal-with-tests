package dev.kaykyfreitas.task.manager.api.taskmanagerapi.application.usecase.user.delete;

import dev.kaykyfreitas.task.manager.api.taskmanagerapi.Fixture;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.adapter.outbound.jpa.entity.UserJpaEntity;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.adapter.outbound.jpa.repository.UserJpaRepository;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.application.IntegrationTest;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.user.UserGateway;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import static org.junit.jupiter.api.Assertions.assertEquals;

@IntegrationTest
class DeleteUserUseCaseIntegrationTest {

    @Autowired
    private DeleteUserUseCase useCase;

    @Autowired
    private UserJpaRepository userJpaRepository;

    @MockitoSpyBean
    private UserGateway userGateway;

    @Test
    void givenValidCommand_whenCallsDeleteUser_shouldDeleteUser() {
        // Given
        assertEquals(0, userJpaRepository.count());

        final var user = Fixture.Users.john();

        userJpaRepository.save(UserJpaEntity.fromDomain(user));

        assertEquals(1, userJpaRepository.count());

        final var aCommand = DeleteUserCommand.with(user.getId().getValue());

        // When
        useCase.execute(aCommand);

        // Then
        assertEquals(0, userJpaRepository.count());

        Mockito.verify(userGateway, Mockito.times(1)).deleteById(Mockito.any());
    }

    @Test
    void givenInvalidUserId_whenCallsDeleteUser_shouldBeOK() {
        // Given
        assertEquals(0, userJpaRepository.count());

        final var aCommand = DeleteUserCommand.with("invalid-id");

        // When
        useCase.execute(aCommand);

        // Then
        assertEquals(0, userJpaRepository.count());

        Mockito.verify(userGateway, Mockito.times(1)).deleteById(Mockito.any());
    }

}
