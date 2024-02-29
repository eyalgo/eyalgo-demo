package eyalgo.demo.end2end

import eyalgo.demo.adapters.data.exposed.Guests
import eyalgo.demo.adapters.data.exposed.MessageIdToGuestId
import eyalgo.demo.adapters.messaging.GuestCreateMessage
import eyalgo.demo.containers.ContainerActiveMQ
import eyalgo.demo.containers.ContainerMySQL
import eyalgo.demo.domain.model.Guest
import eyalgo.demo.ports.outbound.GuestsRepository
import eyalgo.demo.teststrategies.ExposedTestExtension
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.shouldBe
import io.micronaut.context.annotation.Requires
import io.micronaut.jms.activemq.classic.configuration.ActiveMqClassicConfiguration.CONNECTION_FACTORY_BEAN_NAME
import io.micronaut.jms.annotations.JMSProducer
import io.micronaut.jms.annotations.Queue
import io.micronaut.messaging.annotation.MessageBody
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import io.micronaut.test.extensions.testresources.annotation.TestResourcesProperties
import jakarta.inject.Inject
import java.time.Duration
import java.util.UUID.randomUUID
import org.awaitility.kotlin.atMost
import org.awaitility.kotlin.await
import org.awaitility.kotlin.untilAsserted
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import org.junit.jupiter.api.extension.ExtendWith

@TestInstance(PER_CLASS)
@ExtendWith(ExposedTestExtension::class)
@MicronautTest(
    startApplication = false,
    environments = ["exposed", "end2endTest", "mysql", "messaging"]
)
@TestResourcesProperties(providers = [ContainerActiveMQ::class, ContainerMySQL::class])
class GuestMessageReceivedSavedToDBEnd2End {
    @Inject
    private lateinit var guestsProducer: GuestsProducer

    @Inject
    private lateinit var guestsRepository: GuestsRepository

    @Test
    fun `a message with a guest saves the them in the DB`() {
        val messageId = randomUUID()
        guestsProducer.send(GuestCreateMessage(messageId, "first-name", "last-name"))
        await atMost Duration.ofSeconds(10) untilAsserted  {
            guestsRepository.getGuests() shouldContain Guest("first-name", "last-name")
        }
        var mappedGuestId: Long = -1
        var guestId: Long = -2
        transaction {
            mappedGuestId = MessageIdToGuestId.selectAll()
                .where { MessageIdToGuestId.messageId eq messageId }
                .map { it[MessageIdToGuestId.guestId] }
                .single()

            guestId = Guests.selectAll()
                .where { Guests.firstName eq "first-name" }
                .map { it[Guests.id].value }
                .single()
        }
        mappedGuestId shouldBe guestId
    }

    @JMSProducer(CONNECTION_FACTORY_BEAN_NAME)
    @Requires(env = ["messaging"])
    interface GuestsProducer {

        @Queue("guests")
        fun send(@MessageBody guestCreateMessage: GuestCreateMessage)
    }
}
