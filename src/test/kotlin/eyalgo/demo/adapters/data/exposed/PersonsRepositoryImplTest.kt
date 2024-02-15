package eyalgo.demo.adapters.data.exposed

import eyalgo.demo.domain.model.Person
import eyalgo.demo.ports.PersonRepository
import io.kotest.matchers.shouldBe
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Test

@MicronautTest
class PersonsRepositoryImplTest {
    @Inject
    lateinit var repo: PersonRepository

    @Test
    fun `create and get a person`() {
        val person = Person("Eyal", "Golan")
        val id = repo.createPerson(person)

        repo.getPerson(id) shouldBe person
    }
}
