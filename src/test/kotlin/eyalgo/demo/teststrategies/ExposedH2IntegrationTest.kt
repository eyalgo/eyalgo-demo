package eyalgo.demo.teststrategies

import eyalgo.demo.domain.model.Guest
import eyalgo.demo.ports.inbound.AddGuests
import io.micronaut.context.annotation.Requires
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Singleton
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import org.junit.jupiter.api.extension.ExtendWith

@TestInstance(PER_CLASS)
@ExtendWith(ExposedTestExtension::class)
@MicronautTest(
    startApplication = false,
    environments = ["exposed", "integrationTest", "h2", "stubbing"]
)
annotation class ExposedH2IntegrationTest

const val STUBBED_GUEST_ID = 79L

@Singleton
@Requires(env = ["stubbing"])
class StubbedAddGuests : AddGuests {
    override fun add(guest: Guest): Long = STUBBED_GUEST_ID
}
