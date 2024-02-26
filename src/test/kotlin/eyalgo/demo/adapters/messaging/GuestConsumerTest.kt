package eyalgo.demo.adapters.messaging

import eyalgo.demo.domain.CreateGuestService
import eyalgo.demo.domain.model.Guest
import io.mockk.mockk
import io.mockk.verify
import java.util.UUID.randomUUID
import org.junit.jupiter.api.Test

class GuestConsumerTest {
    private val service: CreateGuestService = mockk(relaxed = true)

    private val consumer = GuestConsumer(service)

    @Test
    fun `verify call to the service`() {
        val messageId = randomUUID()
        val guestMessage = GuestCreateMessage(messageId, "firstName", "lastName")
        consumer.receive(guestMessage)
        verify { service.createGuest(messageId, Guest(guestMessage.firstName, guestMessage.lastName)) }
    }
}
