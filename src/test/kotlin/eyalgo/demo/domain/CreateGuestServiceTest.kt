package eyalgo.demo.domain

import eyalgo.demo.domain.model.Guest
import eyalgo.demo.ports.outbound.GuestsRepository
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.util.UUID.randomUUID
import org.junit.jupiter.api.Test

class CreateGuestServiceTest {
    private val repository: GuestsRepository = mockk(relaxed = true)

    private val service = CreateGuestService(repository)

    @Test
    fun `should call the repository and return the id`() {
        val id = 12L
        every { repository.createGuest(any()) } returns id

        val guest = Guest("some name", "some last name")
        service.add(guest) shouldBe id
        verify { repository.createGuest(guest) }
    }

    @Test
    fun `should call with messageId the repository and return the id`() {
        val id = 12L
        every { repository.createGuest(any(), any()) } returns id

        val guest = Guest("some name", "some last name")
        val messageId = randomUUID()
        service.createGuest(messageId, guest) shouldBe id
        verify { repository.createGuest(messageId, guest) }
    }
}
