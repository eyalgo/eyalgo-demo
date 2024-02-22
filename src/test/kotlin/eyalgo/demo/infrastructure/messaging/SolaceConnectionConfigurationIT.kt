package eyalgo.demo.infrastructure.messaging

import eyalgo.demo.SimpleIntegrationTest
import io.kotest.matchers.shouldBe
import jakarta.inject.Inject
import org.junit.jupiter.api.Test

@SimpleIntegrationTest
class SolaceConnectionConfigurationIT {
    @Inject
    lateinit var configuration: SolaceConnectionConfiguration


    @Test
    fun `verify configuration`() {
        configuration.url shouldBe "amqp://localhost:5672"
        configuration.user shouldBe "admin"
        configuration.password shouldBe "admin"
    }
}
