package eyalgo.demo.teststrategies

import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import org.junit.jupiter.api.extension.ExtendWith

@TestInstance(PER_CLASS)
@ExtendWith(ExposedTestExtension::class)
@MicronautTest(
    startApplication = false,
    environments = ["exposed", "integrationTest", "postgres"]
)
annotation class ExposedIntegrationTest
