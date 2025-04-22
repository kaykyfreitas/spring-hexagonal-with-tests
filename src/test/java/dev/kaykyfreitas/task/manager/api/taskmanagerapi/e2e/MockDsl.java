package dev.kaykyfreitas.task.manager.api.taskmanagerapi.e2e;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.adapter.inbound.api.model.request.CreateUserRequest;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.adapter.inbound.api.model.request.UpdateUserRequest;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.Identifier;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.domain.user.UserId;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public interface MockDsl {

    MockMvc mvc();

    ObjectMapper objectMapper();

    default UserId givenAUser(final String name, final String username, final String email) throws Exception {
        final var requestBody = new CreateUserRequest(name, username, email);
        final var userId = given("/users", requestBody);
        return UserId.from(userId);
    }

    default ResultActions updateAUser(final UserId userId, final UpdateUserRequest request) throws Exception {
        return this.update("/users/", userId, request);
    }

    private String given(final String url, final Object body) throws Exception {
        final var request = MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper().writeValueAsString(body));

        return this.mvc().perform(request)
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getHeader("Location")
                .replace("%s/".formatted(url), "");
    }

    private ResultActions update(final String url, final Identifier anId, final Object aRequestBody) throws Exception {
        final var request = MockMvcRequestBuilders.put(url + anId.getValue())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper().writeValueAsString(aRequestBody));

        return this.mvc().perform(request);
    }
}
