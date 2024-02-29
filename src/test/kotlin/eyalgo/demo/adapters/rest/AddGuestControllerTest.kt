package eyalgo.demo.adapters.rest

import eyalgo.demo.domain.model.Guest
import eyalgo.demo.ports.inbound.AddGuests
import io.kotest.matchers.optional.shouldBePresent
import io.kotest.matchers.shouldBe
import io.micronaut.http.HttpStatus.CREATED
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

class AddGuestControllerTest {
    private val addGuests: AddGuests = mockk(relaxed = true)
    private val controller = AddGuestController(addGuests)

    @Test
    fun `add guest calls propagates the call`() {
        every { addGuests.add(any()) } returns 13

        val response = controller.addGuest(GuestDTO("Elvis", "Presley"))
        response.status shouldBe CREATED
        response.body shouldBePresent { it shouldBe 13 }

        verify { addGuests.add(Guest("Elvis", "Presley")) }
    }
}
