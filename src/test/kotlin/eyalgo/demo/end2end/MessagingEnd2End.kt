package eyalgo.demo.end2end

import eyalgo.demo.adapters.messaging.TextConsumer
import eyalgo.demo.adapters.messaging.TextProducer
import eyalgo.demo.containers.ContainerActiveMQ
import io.kotest.matchers.collections.shouldNotContain
import io.micronaut.context.ApplicationContext
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import io.micronaut.test.extensions.testresources.annotation.TestResourcesProperties
import jakarta.inject.Inject
import java.time.Duration
import nl.altindag.log.LogCaptor
import org.awaitility.kotlin.atMost
import org.awaitility.kotlin.await
import org.awaitility.kotlin.untilAsserted
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS

@TestInstance(PER_CLASS)
@MicronautTest(
    startApplication = false,
    environments = ["messaging"]
)
@TestResourcesProperties(providers = [ContainerActiveMQ::class])
class MessagingEnd2End {
    private val logCaptor: LogCaptor = LogCaptor.forClass(TextConsumer::class.java)

    @Inject
    private lateinit var context: ApplicationContext

    @Inject lateinit var producer: TextProducer

    @Test
    fun `verify send and receive a message`() {
        producer.send("Hello World!")

        await atMost Duration.ofSeconds(20) untilAsserted {
            logCaptor.infoLogs shouldNotContain "Running sample job"
        }
    }
}
