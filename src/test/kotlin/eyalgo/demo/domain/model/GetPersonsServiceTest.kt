package eyalgo.demo.domain.model

import eyalgo.demo.domain.GetPersonsService
import eyalgo.demo.ports.PersonRepository
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.shouldBe
import io.mockk.mockk
import nl.altindag.log.LogCaptor
import org.junit.jupiter.api.Test

class GetPersonsServiceTest {
    private val repository: PersonRepository = mockk()
    private val service = GetPersonsService(repository)

    private val logCaptor: LogCaptor = LogCaptor.forClass(GetPersonsService::class.java)

    @Test
    fun `should return the hard-coded list of persons`() {
        service.getPersons() shouldBe listOf(
            Person("firstName-1", "lastName-1"),
            Person("firstName-2", "lastName-2")
        )

        logCaptor.infoLogs shouldContain "getting persons"
    }
}
