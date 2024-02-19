package eyalgo.demo

import io.micronaut.context.DefaultApplicationContextBuilder
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import org.junit.jupiter.api.extension.ExtendWith


@TestInstance(PER_CLASS)
@ExtendWith(IntegrationTestExtension::class)
@MicronautTest(
    contextBuilder = [CustomContextBuilder::class],
    startApplication = false,
    environments = ["exposed", "integrationTest"]
)
annotation class IntegrationTest

class CustomContextBuilder: DefaultApplicationContextBuilder() {
    init {
        eagerInitSingletons(false)
    }
}
