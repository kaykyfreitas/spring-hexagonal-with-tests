package dev.kaykyfreitas.task.manager.api.taskmanagerapi.application.usecase.user.retrieve.list;

import dev.kaykyfreitas.task.manager.api.taskmanagerapi.Fixture;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.adapter.outbound.jpa.entity.UserJpaEntity;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.adapter.outbound.jpa.repository.UserJpaRepository;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.application.IntegrationTest;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.pagination.SearchQuery;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.user.UserGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@IntegrationTest
public class ListUsersUseCaseIntegrationTest {

    @Autowired
    private ListUsersUseCase useCase;

    @Autowired
    private UserJpaRepository userJpaRepository;

    @MockitoSpyBean
    private UserGateway userGateway;

    @Test
    void givenValidQuery_whenCallsListUsers_shouldReturnAll() {
        // Given
        assertEquals(0, userJpaRepository.count());

        final var users = List.of(Fixture.Users.john(), Fixture.Users.jane());

        userJpaRepository.saveAll(users.stream().map(UserJpaEntity::fromDomain).toList());

        assertEquals(2, userJpaRepository.count());

        final var expectedPage = 0;
        final var expectedPerPage = 10;
        final var expectedTerms = "jo";
        final var expectedSort = "createdAt";
        final var expectedDirection = "asc";
        final var expectedTotal = 1;

        final var expectedItems = users.stream()
                .filter(user -> user.getName().toLowerCase().contains(expectedTerms.toLowerCase()))
                .map(ListUserOutput::from)
                .toList();

        final var query = new SearchQuery(expectedPage, expectedPerPage, expectedTerms, expectedSort, expectedDirection);

        // When
        final var actualOutput = useCase.execute(query);

        // Then
        assertEquals(expectedPage, actualOutput.currentPage());
        assertEquals(expectedPerPage, actualOutput.perPage());
        assertEquals(expectedTotal, actualOutput.total());
        assertEquals(expectedItems, actualOutput.items());

        verify(userGateway, times(1)).findAll(any());
    }

    @Test
    void givenValidQuery_whenCallsListUsersAndIsEmpty_shouldReturnEmpty() {
        // Given
        assertEquals(0, userJpaRepository.count());

        final var expectedPage = 0;
        final var expectedPerPage = 10;
        final var expectedTerms = "";
        final var expectedSort = "createdAt";
        final var expectedDirection = "asc";
        final var expectedTotal = 0;

        final var expectedItems = List.<ListUserOutput>of();

        final var query = new SearchQuery(expectedPage, expectedPerPage, expectedTerms, expectedSort, expectedDirection);

        // When
        final var actualOutput = useCase.execute(query);

        // Then
        Assertions.assertEquals(expectedPage, actualOutput.currentPage());
        Assertions.assertEquals(expectedPerPage, actualOutput.perPage());
        Assertions.assertEquals(expectedTotal, actualOutput.total());
        Assertions.assertEquals(expectedItems, actualOutput.items());

        verify(userGateway, times(1)).findAll(any());
    }

}
