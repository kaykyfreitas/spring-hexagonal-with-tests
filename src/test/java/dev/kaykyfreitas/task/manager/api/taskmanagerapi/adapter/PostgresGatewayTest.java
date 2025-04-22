package dev.kaykyfreitas.task.manager.api.taskmanagerapi.adapter;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.ActiveProfiles;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@DataJpaTest
@ActiveProfiles("integration-test")
@ComponentScan(
        basePackages = "dev.kaykyfreitas.task.manager.api.taskmanagerapi",
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = ".[PostgresGateway]")
        }
)
@ExtendWith(PostgresCleanUpExtension.class)
public @interface PostgresGatewayTest {
}
