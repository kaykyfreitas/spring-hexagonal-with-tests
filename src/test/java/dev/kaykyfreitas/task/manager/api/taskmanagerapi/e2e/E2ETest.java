package dev.kaykyfreitas.task.manager.api.taskmanagerapi.e2e;

import dev.kaykyfreitas.task.manager.api.taskmanagerapi.Application;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.adapter.PostgresCleanUpExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@ActiveProfiles("e2e-test")
@SpringBootTest(classes = Application.class)
@ExtendWith(PostgresCleanUpExtension.class)
@AutoConfigureMockMvc
public @interface E2ETest {
}
