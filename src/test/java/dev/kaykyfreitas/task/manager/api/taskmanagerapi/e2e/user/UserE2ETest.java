package dev.kaykyfreitas.task.manager.api.taskmanagerapi.e2e.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.adapter.inbound.api.model.request.CreateUserRequest;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.adapter.inbound.api.model.request.UpdateUserRequest;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.adapter.outbound.jpa.repository.UserJpaRepository;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.e2e.E2ETest;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.e2e.MockDsl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@E2ETest
@Testcontainers
public class UserE2ETest implements MockDsl {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public MockMvc mvc() {
        return this.mockMvc;
    }

    @Override
    public ObjectMapper objectMapper() {
        return this.objectMapper;
    }

    @Container
    private static final PostgreSQLContainer<?> POSTGRES_CONTAINER = new PostgreSQLContainer<>("postgres:latest")
            .withPassword("P@ssw0rd")
            .withUsername("postgres")
            .withDatabaseName("task_manager");

    @DynamicPropertySource
    public static void setDatasourceProperties(final DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRES_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES_CONTAINER::getUsername);
        registry.add("spring.datasource.password", POSTGRES_CONTAINER::getPassword);
    }

    @Test
    void asAUserIShouldBeAbleToCreateANewUser() throws Exception {
        assertTrue(POSTGRES_CONTAINER.isRunning());
        assertEquals(0, userJpaRepository.count());

        final var expectedName = "John Doe";
        final var expectedUsername = "johndoe";
        final var expectedEmail = "johndoe@email.com";

        final var userId = givenAUser(expectedName, expectedUsername, expectedEmail);

        assertEquals(1, userJpaRepository.count());

        final var user = userJpaRepository.findById(userId.getValue()).get();

        assertEquals(expectedName, user.getName());
        assertEquals(expectedUsername, user.getUsername());
        assertEquals(expectedEmail, user.getEmail());
        assertNotNull(user.getCreatedAt());
        assertNotNull(user.getUpdatedAt());
    }

    @Test
    void asUserIShouldBeAbleToSeeATreatedErrorUsingInvalidName() throws Exception {
        assertTrue(POSTGRES_CONTAINER.isRunning());
        assertEquals(0, userJpaRepository.count());

        final var expectedName = "";
        final var expectedUsername = "johndoe";
        final var expectedEmail = "johndoe@email.com";

        final var requestBody = new CreateUserRequest(expectedName, expectedUsername, expectedEmail);

        final var request = MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper().writeValueAsString(requestBody));

        this.mockMvc.perform(request)
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.message", equalTo("could not create an user")));
    }

    @Test
    void asUserIShouldBeAbleUpdateAnExistentUser() throws Exception {
        assertTrue(POSTGRES_CONTAINER.isRunning());
        assertEquals(0, userJpaRepository.count());

        final var expectedName = "John Doe";
        final var expectedUsername = "johndoe";
        final var expectedEmail = "johndoe@email.com";

        final var userId = givenAUser(expectedName, expectedUsername, expectedEmail);

        assertEquals(1, userJpaRepository.count());

        final var user = userJpaRepository.findById(userId.getValue()).get();

        assertEquals(expectedName, user.getName());
        assertEquals(expectedUsername, user.getUsername());
        assertEquals(expectedEmail, user.getEmail());
        assertNotNull(user.getCreatedAt());
        assertNotNull(user.getUpdatedAt());

        final var expectedUpdatedName = "John Doe Updated";
        final var expectedUpdatedEmail = "johndoeupdated@email.com";

        final var requestBody = new UpdateUserRequest(expectedUpdatedName, expectedUpdatedEmail);

        updateAUser(userId, requestBody);

        final var updatedUser = userJpaRepository.findById(userId.getValue()).get();

        assertEquals(expectedUpdatedName, updatedUser.getName());
        assertEquals(user.getUsername(), updatedUser.getUsername());
        assertEquals(expectedUpdatedEmail, updatedUser.getEmail());
        assertEquals(user.getCreatedAt(), updatedUser.getCreatedAt());
        assertTrue(user.getUpdatedAt().isBefore(updatedUser.getUpdatedAt()));
    }

}
