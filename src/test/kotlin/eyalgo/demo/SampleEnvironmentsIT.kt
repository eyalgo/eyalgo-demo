package eyalgo.demo

import io.kotest.matchers.collections.shouldContain
import io.micronaut.context.ApplicationContext
import jakarta.inject.Inject
import org.junit.jupiter.api.Test

@SimpleIntegrationTest
class SampleEnvironmentsIT {
    @Inject
    lateinit var context: ApplicationContext

    @Test
    fun `check environments example`() {
        val environment = context.environment

        environment.activeNames shouldContain "test"
        environment.activeNames shouldContain "integrationTest"
    }
}
