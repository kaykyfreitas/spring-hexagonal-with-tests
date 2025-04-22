package dev.kaykyfreitas.task.manager.api.taskmanagerapi.application.usecase.user.update;

import dev.kaykyfreitas.task.manager.api.taskmanagerapi.Fixture;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.adapter.outbound.jpa.entity.UserJpaEntity;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.adapter.outbound.jpa.repository.UserJpaRepository;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.application.IntegrationTest;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.exception.NotFoundException;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.user.UserGateway;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import static org.junit.jupiter.api.Assertions.*;

@IntegrationTest
class UpdateUserUseCaseIntegrationTest {

    @Autowired
    private UpdateUserUseCase useCase;

    @Autowired
    private UserJpaRepository userJpaRepository;

    @MockitoSpyBean
    private UserGateway userGateway;

    @Test
    void givenValidParams_whenCallUpdateUser_shouldReturnUser() {
        // Given
        assertEquals(0, userJpaRepository.count());

        final var john = Fixture.Users.john();

        userJpaRepository.save(UserJpaEntity.fromDomain(john));

        assertEquals(1, userJpaRepository.count());

        final var expectedUpdateName = "updated name";
        final var expectedUpdateEmail = "updated@email.com";

        final var command = UpdateUserCommand.with(
                john.getId().getValue(),
                expectedUpdateName,
                expectedUpdateEmail
        );

        // When
        final var actualOutput = useCase.execute(command);

        // Then
        assertEquals(1, userJpaRepository.count());

        assertNotNull(actualOutput);
        assertNotNull(actualOutput.id());

        final var actualUser = userJpaRepository.findById(actualOutput.id()).get();

        assertEquals(actualOutput.id(), actualUser.getId());
        assertEquals(expectedUpdateName, actualUser.getName());
        assertEquals(john.getUsername(), actualUser.getUsername());
        assertEquals(expectedUpdateEmail, actualUser.getEmail());
        assertNotNull(actualUser.getCreatedAt());
        assertNotNull(actualUser.getUpdatedAt());
        assertTrue(actualUser.getCreatedAt().isBefore(actualUser.getUpdatedAt()));

        Mockito.verify(userGateway, Mockito.times(1)).findById(Mockito.any());
        Mockito.verify(userGateway, Mockito.times(1)).update(Mockito.any());
    }

    @Test
    void givenInexistentUser_whenCallUpdateUser_shouldReceiveNotFoundError() {
        // Given
        assertEquals(0, userJpaRepository.count());

        final var expectedId = "123-456-789";
        final var expectedUpdateName = "updated name";
        final var expectedUpdateEmail = "updated@email.com";

        final var expectedErrorMessage = "user with id %s was not found".formatted(expectedId);

        final var command = UpdateUserCommand.with(
                expectedId,
                expectedUpdateName,
                expectedUpdateEmail
        );

        // When
        final var actualException = assertThrows(
                NotFoundException.class,
                () -> useCase.execute(command)
        );

        // Then

        assertEquals(0, userJpaRepository.count());

        assertNotNull(actualException);
        assertEquals(expectedErrorMessage, actualException.getMessage());

        Mockito.verify(userGateway, Mockito.times(1)).findById(Mockito.any());
        Mockito.verify(userGateway, Mockito.times(0)).update(Mockito.any());
    }

}
