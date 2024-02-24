package eyalgo.demo.adapters.rest

import eyalgo.demo.domain.GetGuestsService
import eyalgo.demo.domain.model.Guest
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test

class GetGuestsControllerTest {
    private val service = mockk<GetGuestsService>()
    private val controller = GetGuestsController(service)
    @Test
    fun `return the guests from the service`() {
        every { service.getGuests() } returns listOf(
            Guest("a", "b")
        )
        controller.getGuests() shouldBe listOf(GuestDTO("a", "b"))
    }
}
