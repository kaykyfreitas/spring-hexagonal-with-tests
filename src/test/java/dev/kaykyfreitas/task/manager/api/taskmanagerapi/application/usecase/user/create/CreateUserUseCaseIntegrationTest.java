package dev.kaykyfreitas.task.manager.api.taskmanagerapi.application.usecase.user.create;

import dev.kaykyfreitas.task.manager.api.taskmanagerapi.Fixture;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.adapter.outbound.jpa.repository.UserJpaRepository;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.application.IntegrationTest;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.exception.NotificationException;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.user.UserGateway;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import static org.junit.jupiter.api.Assertions.*;

@IntegrationTest
class CreateUserUseCaseIntegrationTest {

    @Autowired
    private CreateUserUseCase useCase;

    @Autowired
    private UserJpaRepository userJpaRepository;

    @MockitoSpyBean
    private UserGateway userGateway;

    @Test
    void givenAValidCommand_whenCallsCreateUser_shouldReturnUser() {
        // Given
        assertEquals(0, userJpaRepository.count());
        final var expectedName = Fixture.Users.name();
        final var expectedUsername = Fixture.Users.username();
        final var expectedEmail = Fixture.Users.email();

        final var aCommand = CreateUserCommand.with(expectedName, expectedUsername, expectedEmail);

        // When
        final var actualOutput = useCase.execute(aCommand);

        // Then
        assertNotNull(actualOutput);
        assertNotNull(actualOutput.id());

        assertEquals(1, userJpaRepository.count());

        final var actualUser = userJpaRepository.findById(actualOutput.id()).get();

        assertEquals(actualOutput.id(), actualUser.getId());
        assertEquals(expectedName, actualUser.getName());
        assertEquals(expectedUsername, actualUser.getUsername());
        assertEquals(expectedEmail, actualUser.getEmail());
        assertNotNull(actualUser.getCreatedAt());
        assertNotNull(actualUser.getUpdatedAt());
        assertEquals(actualUser.getCreatedAt(), actualUser.getUpdatedAt());

        Mockito.verify(userGateway, Mockito.times(1)).create(Mockito.any());
    }

    @Test
    void givenInvalidNullName_whenCallCreateUser_shouldReturnUser() {
        // Given
        assertEquals(0, userJpaRepository.count());

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
        assertEquals(0, userJpaRepository.count());

        assertNotNull(actualException);
        assertEquals(expectedErrorCount, actualException.getErrors().size());
        assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());

        Mockito.verify(userGateway, Mockito.times(0)).create(Mockito.any());
    }

}
