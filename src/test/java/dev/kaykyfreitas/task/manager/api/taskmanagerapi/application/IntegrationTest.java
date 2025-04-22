package dev.kaykyfreitas.task.manager.api.taskmanagerapi.application;

import dev.kaykyfreitas.task.manager.api.taskmanagerapi.Application;
import dev.kaykyfreitas.task.manager.api.taskmanagerapi.adapter.PostgresCleanUpExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@ActiveProfiles("integration-test")
@SpringBootTest(classes = Application.class)
@ExtendWith(PostgresCleanUpExtension.class)
public @interface IntegrationTest {
}
