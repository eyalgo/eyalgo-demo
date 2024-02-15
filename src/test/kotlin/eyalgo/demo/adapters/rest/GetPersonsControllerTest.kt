package eyalgo.demo.adapters.rest

import eyalgo.demo.domain.GetPersonsService
import eyalgo.demo.domain.model.Person
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test

class GetPersonsControllerTest {
    private val service = mockk<GetPersonsService>()
    private val controller = GetPersonsController(service)
    @Test
    fun `return the persons from the service`() {
        every { service.getPersons() } returns listOf(
            Person("a", "b")
        )
        controller.getPersons() shouldBe listOf(Person("a", "b"))
    }
}
