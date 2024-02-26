package eyalgo.demo.domain

import eyalgo.demo.domain.model.Guest
import eyalgo.demo.ports.GuestsRepository
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.util.UUID.randomUUID
import org.junit.jupiter.api.Test

class CreateGuestServiceTest {
    private val guestsRepository: GuestsRepository = mockk(relaxed = true)

    private val service = CreateGuestService(guestsRepository)

    @Test
    fun `should call the repository and return the id`() {
        val id = 12L
        every { guestsRepository.createGuest(any(), any()) } returns id

        val guest = Guest("some name", "some last name")
        val messageId = randomUUID()
        service.createGuest(messageId, guest) shouldBe id
        verify { guestsRepository.createGuest(messageId, guest) }
    }
}
