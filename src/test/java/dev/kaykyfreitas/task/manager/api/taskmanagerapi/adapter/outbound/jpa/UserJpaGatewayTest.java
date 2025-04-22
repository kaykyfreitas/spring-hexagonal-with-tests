package dev.kaykyfreitas.task.manager.api.taskmanagerapi.adapter.outbound.jpa;

import dev.kaykyfreitas.task.manager.api.taskmanagerapi.Fixture;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.adapter.PostgresGatewayTest;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.adapter.outbound.jpa.repository.UserJpaRepository;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.pagination.SearchQuery;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.user.UserId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

@PostgresGatewayTest
class UserJpaGatewayTest {

    @Autowired
    private UserPostgresGateway userPostgresGateway;

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Test
    void givenAValidUser_whenCallsCreate_shouldReturnANewUser() {
        // Given
        assertEquals(0, userJpaRepository.count());

        final var user = Fixture.Users.john();

        // When
        final var createdUser = userPostgresGateway.create(user);

        // Then
        assertEquals(1, userJpaRepository.count());

        assertNotNull(createdUser);
        assertEquals(user.getId(), createdUser.getId());
        assertEquals(user.getName(), createdUser.getName());
        assertEquals(user.getUsername(), createdUser.getUsername());
        assertEquals(user.getEmail(), createdUser.getEmail());
        assertEquals(user.getCreatedAt(), createdUser.getCreatedAt());
        assertEquals(user.getUpdatedAt(), createdUser.getUpdatedAt());

        final var actualEntity = userJpaRepository.findById(user.getId().getValue()).get();

        assertNotNull(actualEntity);
        assertEquals(user.getId().getValue(), actualEntity.getId());
        assertEquals(user.getName(), actualEntity.getName());
        assertEquals(user.getUsername(), actualEntity.getUsername());
        assertEquals(user.getEmail(), actualEntity.getEmail());
        assertEquals(user.getCreatedAt(), actualEntity.getCreatedAt());
        assertEquals(user.getUpdatedAt(), actualEntity.getUpdatedAt());
    }

    @Test
    void givenAValidUser_whenCallsUpdate_shouldReturnAnUpdatedUser() {
        // Given
        assertEquals(0, userJpaRepository.count());

        final var user = Fixture.Users.john();

        userPostgresGateway.create(user);

        assertEquals(1, userJpaRepository.count());

        final var actualEntity = userJpaRepository.findById(user.getId().getValue()).get();

        final var updatedName = "Updated Name";
        final var updatedEmail = "updated@email.com";

        final var updatedUser = user.update(updatedName, updatedEmail);

        // When
        final var updatedUserResult = userPostgresGateway.update(updatedUser);

        // Then
        assertEquals(1, userJpaRepository.count());

        assertNotNull(updatedUserResult);
        assertEquals(updatedUser.getId(), updatedUserResult.getId());
        assertEquals(updatedUser.getName(), updatedUserResult.getName());
        assertEquals(updatedUser.getUsername(), updatedUserResult.getUsername());
        assertEquals(updatedUser.getEmail(), updatedUserResult.getEmail());
        assertEquals(updatedUser.getCreatedAt(), updatedUserResult.getCreatedAt());
        assertEquals(updatedUser.getUpdatedAt(), updatedUserResult.getUpdatedAt());

        final var actualUpdatedEntity = userJpaRepository.findById(user.getId().getValue()).get();

        assertNotNull(actualUpdatedEntity);
        assertEquals(updatedUser.getId().getValue(), actualUpdatedEntity.getId());
        assertEquals(updatedUser.getName(), actualUpdatedEntity.getName());
        assertEquals(updatedUser.getUsername(), actualUpdatedEntity.getUsername());
        assertEquals(updatedUser.getEmail(), actualUpdatedEntity.getEmail());
        assertEquals(updatedUser.getCreatedAt(), actualUpdatedEntity.getCreatedAt());
        assertEquals(updatedUser.getUpdatedAt(), actualUpdatedEntity.getUpdatedAt());

        assertEquals(actualEntity.getId(), actualUpdatedEntity.getId());
    }

    @Test
    void givenAPrePersistedUserAndValidUserId_whenCallsDeleteById_shouldDeleteUser() {
        // Given
        assertEquals(0, userJpaRepository.count());

        final var user = Fixture.Users.john();

        userPostgresGateway.create(user);

        assertEquals(1, userJpaRepository.count());

        // When
        userPostgresGateway.deleteById(user.getId());

        // Then
        assertEquals(0, userJpaRepository.count());
    }

    @Test
    void givenAndInvalidUserId_whenCallsDeleteById_shouldBeOk() {
        // Given
        assertEquals(0, userJpaRepository.count());

        // When
        userPostgresGateway.deleteById(UserId.from("invalid"));

        // Then
        assertEquals(0, userJpaRepository.count());
    }

    @Test
    void givenPrePersistedUserAndAValidUserId_whenFindById_shouldReturnAUser() {
        // Given
        assertEquals(0, userJpaRepository.count());

        final var user = Fixture.Users.john();

        final var createdUser = userPostgresGateway.create(user);

        assertEquals(1, userJpaRepository.count());

        // When
        final var actualUserOpt = userPostgresGateway.findById(createdUser.getId());

        // Then
        assertTrue(actualUserOpt.isPresent());
        assertEquals(createdUser.getId(), actualUserOpt.get().getId());
        assertEquals(createdUser.getName(), actualUserOpt.get().getName());
        assertEquals(createdUser.getUsername(), actualUserOpt.get().getUsername());
        assertEquals(createdUser.getEmail(), actualUserOpt.get().getEmail());
        assertEquals(createdUser.getCreatedAt(), actualUserOpt.get().getCreatedAt());
        assertEquals(createdUser.getUpdatedAt(), actualUserOpt.get().getUpdatedAt());
    }

    @Test
    void givenAnInvalidUserId_whenFindById_shouldReturnEmpty() {
        // Given
        assertEquals(0, userJpaRepository.count());

        final var invalidUserId = UserId.from("invalid");

        // When
        final var actualUserOpt = userPostgresGateway.findById(invalidUserId);

        // Then
        assertTrue(actualUserOpt.isEmpty());
    }

    @Test
    void givenAPrePersistedUserQueryOrderedAsc_whenFindAll_shouldReturnAllUsersPaginatedOrderedAsc() {
        // Given
        assertEquals(0, userJpaRepository.count());

        final var expectedPage = 0;
        final var expectedPerPage = 1;
        final var expectedTotal = 2;

        final var john = Fixture.Users.john();
        final var jane = Fixture.Users.jane();

        userPostgresGateway.create(john);
        userPostgresGateway.create(jane);

        assertEquals(2, userJpaRepository.count());

        final var query = new SearchQuery(0, 1, "", "name", "asc");

        // When
        final var actualResult = userPostgresGateway.findAll(query);

        // Then
        Assertions.assertEquals(expectedPage, actualResult.currentPage());
        Assertions.assertEquals(expectedPerPage, actualResult.perPage());
        Assertions.assertEquals(expectedTotal, actualResult.total());
        Assertions.assertEquals(expectedPerPage, actualResult.items().size());
        Assertions.assertEquals(jane.getId().getValue(), actualResult.items().getFirst().getId().getValue());
    }

    @Test
    void givenAPrePersistedUserQueryOrderedDesc_whenFindAll_shouldReturnAllUsersPaginatedOrderedDesc() {
        // Given
        assertEquals(0, userJpaRepository.count());

        final var expectedPage = 0;
        final var expectedPerPage = 1;
        final var expectedTotal = 2;

        final var john = Fixture.Users.john();
        final var jane = Fixture.Users.jane();

        userPostgresGateway.create(john);
        userPostgresGateway.create(jane);

        assertEquals(2, userJpaRepository.count());

        final var query = new SearchQuery(0, 1, "", "name", "desc");

        // When
        final var actualResult = userPostgresGateway.findAll(query);

        // Then
        Assertions.assertEquals(expectedPage, actualResult.currentPage());
        Assertions.assertEquals(expectedPerPage, actualResult.perPage());
        Assertions.assertEquals(expectedTotal, actualResult.total());
        Assertions.assertEquals(expectedPerPage, actualResult.items().size());
        Assertions.assertEquals(john.getId().getValue(), actualResult.items().getFirst().getId().getValue());
    }

    @Test
    void givenAPrePersistedUserQueryWithTerm_whenFindAll_shouldReturnAllUsersPaginatedFilteredByTerms() {
        // Given
        assertEquals(0, userJpaRepository.count());

        final var expectedPage = 0;
        final var expectedPerPage = 1;
        final var expectedTotal = 1;

        final var john = Fixture.Users.john();
        final var jane = Fixture.Users.jane();

        userPostgresGateway.create(john);
        userPostgresGateway.create(jane);

        assertEquals(2, userJpaRepository.count());

        final var query = new SearchQuery(0, 1, "ja", "name", "desc");

        // When
        final var actualResult = userPostgresGateway.findAll(query);

        // Then
        Assertions.assertEquals(expectedPage, actualResult.currentPage());
        Assertions.assertEquals(expectedPerPage, actualResult.perPage());
        Assertions.assertEquals(expectedTotal, actualResult.total());
        Assertions.assertEquals(expectedPerPage, actualResult.items().size());
        Assertions.assertEquals(jane.getId().getValue(), actualResult.items().getFirst().getId().getValue());
    }

    @Test
    void givenFollowPagination_whenCallsFindAllWithPage1_shouldReturnPaginated() {
        // Given
        assertEquals(0, userJpaRepository.count());

        var expectedPage = 0;
        final var expectedPerPage = 1;
        final var expectedTotal = 2;

        final var john = Fixture.Users.john();
        final var jane = Fixture.Users.jane();

        userPostgresGateway.create(john);
        userPostgresGateway.create(jane);

        assertEquals(2, userJpaRepository.count());

        var query = new SearchQuery(0, 1, "", "name", "asc");

        final var resultPage0 = userPostgresGateway.findAll(query);

        Assertions.assertEquals(expectedPage, resultPage0.currentPage());
        Assertions.assertEquals(expectedPerPage, resultPage0.perPage());
        Assertions.assertEquals(expectedTotal, resultPage0.total());
        Assertions.assertEquals(expectedPerPage, resultPage0.items().size());
        Assertions.assertEquals(jane.getId().getValue(), resultPage0.items().getFirst().getId().getValue());

        expectedPage = 1;
        query = new SearchQuery(1, 1, "", "name", "asc");
        // When

        final var actualResult = userPostgresGateway.findAll(query);

        // Then
        Assertions.assertEquals(expectedPage, actualResult.currentPage());
        Assertions.assertEquals(expectedPerPage, actualResult.perPage());
        Assertions.assertEquals(expectedTotal, actualResult.total());
        Assertions.assertEquals(expectedPerPage, actualResult.items().size());
        Assertions.assertEquals(john.getId().getValue(), actualResult.items().getFirst().getId().getValue());
    }

    @Test
    void givenEmptyTable_whenCallsFindAll_shouldReturnEmptyPage() {
        // Given
        assertEquals(0, userJpaRepository.count());

        final var expectedPage = 0;
        final var expectedPerPage = 1;
        final var expectedTotal = 0;

        final var query = new SearchQuery(0, 1, "", "name", "asc");

        // When
        final var actualResult = userPostgresGateway.findAll(query);

        // Then
        Assertions.assertEquals(expectedPage, actualResult.currentPage());
        Assertions.assertEquals(expectedPerPage, actualResult.perPage());
        Assertions.assertEquals(expectedTotal, actualResult.total());
        Assertions.assertEquals(expectedTotal, actualResult.items().size());
    }

}