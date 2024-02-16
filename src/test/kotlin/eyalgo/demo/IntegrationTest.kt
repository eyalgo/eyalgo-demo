package eyalgo.demo

import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS


@TestInstance(PER_CLASS)
@MicronautTest(
    startApplication = false,
    environments = ["exposed"]
)
annotation class IntegrationTest
