package eyalgo.demo.domain.model

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class GetPersonsServiceTest {
    private val service = GetPersonsService()

    @Test
    fun `should return the hard-coded list of persons`() {
        service.getPersons() shouldBe listOf(
            Person("firstName-1", "lastName-1"),
            Person("firstName-2", "lastName-2")
        )
    }
}
