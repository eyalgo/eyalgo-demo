package eyalgo.demo.end2end

import eyalgo.demo.adapters.messaging.GuestCreateMessage
import eyalgo.demo.containers.ContainerActiveMQ
import io.micronaut.context.annotation.Requires
import io.micronaut.jms.activemq.classic.configuration.ActiveMqClassicConfiguration.CONNECTION_FACTORY_BEAN_NAME
import io.micronaut.jms.annotations.JMSProducer
import io.micronaut.jms.annotations.Queue
import io.micronaut.messaging.annotation.MessageBody
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import io.micronaut.test.extensions.testresources.annotation.TestResourcesProperties
import jakarta.inject.Inject
import java.util.UUID.randomUUID
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS

@TestInstance(PER_CLASS)
@MicronautTest(
    startApplication = false,
    environments = ["exposed", "end2endTest", "h2", "messaging"]
)
@TestResourcesProperties(providers = [ContainerActiveMQ::class])
class GuestMessageReceivedSavedToDBEnd2End {
    @Inject
    private lateinit var guestsProducer: GuestsProducer

    @JMSProducer(CONNECTION_FACTORY_BEAN_NAME)
    @Requires(env = ["messaging"])
    interface GuestsProducer {

        @Queue("guests")
        fun send(@MessageBody guestCreateMessage: GuestCreateMessage)
    }

    @Test
    fun `a message with a guest saves the them in the DB`() {
       guestsProducer.send(GuestCreateMessage(randomUUID(), "Eyal", "Golan"))
    }
}
