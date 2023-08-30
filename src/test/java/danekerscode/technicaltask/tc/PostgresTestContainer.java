package danekerscode.technicaltask.tc;

import jakarta.validation.constraints.NotNull;
import org.junit.ClassRule;
import org.junit.jupiter.api.AfterAll;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Profiles;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class PostgresTestContainer {


    @ClassRule
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15.1")
            .withPassword("postgres")
            .withUsername("postgres");


    @AfterAll
    static void afterAll() {
        if (postgreSQLContainer.isRunning()) {
            postgreSQLContainer.stop();
        }
    }


    public static class PostgresInitializer implements
            ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(@NotNull ConfigurableApplicationContext configurableApplicationContext) {
            if (configurableApplicationContext.getEnvironment().acceptsProfiles(Profiles
                    .of("docker"))
            ) {
                postgreSQLContainer.start();
                configurableApplicationContext.getEnvironment().getSystemProperties()
                        .put("spring.datasource.url", postgreSQLContainer.getJdbcUrl());
                configurableApplicationContext.getEnvironment().getSystemProperties()
                        .put("spring.datasource.username", postgreSQLContainer.getUsername());
                configurableApplicationContext.getEnvironment().getSystemProperties()
                        .put("spring.datasource.password", postgreSQLContainer.getPassword());
            }
        }
    }

}