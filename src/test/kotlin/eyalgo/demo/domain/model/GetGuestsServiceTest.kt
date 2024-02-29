package eyalgo.demo.domain.model

import eyalgo.demo.domain.GetGuestsService
import eyalgo.demo.ports.outbound.GuestsRepository
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.shouldBe
import io.mockk.mockk
import nl.altindag.log.LogCaptor
import org.junit.jupiter.api.Test

class GetGuestsServiceTest {
    private val repository: GuestsRepository = mockk()
    private val service = GetGuestsService(repository)

    private val logCaptor: LogCaptor = LogCaptor.forClass(GetGuestsService::class.java)

    @Test
    fun `should return the hard-coded list of guests`() {
        service.getGuests() shouldBe listOf(
            Guest("firstName-1", "lastName-1"),
            Guest("firstName-2", "lastName-2")
        )

        logCaptor.infoLogs shouldContain "getting guests"
    }
}
