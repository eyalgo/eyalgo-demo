package eyalgo.demo.adapters.data.joooq

import eyalgo.demo.domain.model.Person
import eyalgo.demo.ports.PersonRepository
import io.kotest.matchers.shouldBe
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

@MicronautTest(environments = ["jooq"])
class PersonRepositoryImplIT {
    @Inject
    lateinit var repo: PersonRepository

    @Test
    @Disabled("Not yet implemented")
    fun `create and get a person`() {
        val person = Person("Eyal", "Golan")
        val id = repo.createPerson(person)

        repo.getPerson(id) shouldBe person
    }
}
