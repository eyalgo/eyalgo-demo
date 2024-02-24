package eyalgo.demo.adapters.data.exposed

import eyalgo.demo.containers.ContainerPostgres
import eyalgo.demo.teststrategies.ExposedIntegrationTest
import eyalgo.demo.domain.model.Person
import eyalgo.demo.ports.PersonRepository
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.shouldBe
import io.micronaut.test.extensions.testresources.annotation.TestResourcesProperties
import jakarta.inject.Inject
import org.junit.jupiter.api.Test

@ExposedIntegrationTest
@TestResourcesProperties(providers = [ContainerPostgres::class])
class PersonsRepositoryImplIT {
    @Inject
    lateinit var repo: PersonRepository

    @Test
    fun `create and get a person`() {
        val person = Person("Eyal", "Golan")
        val id = repo.createPerson(person)

        repo.getPerson(id) shouldBe person
    }

    @Test
    fun `get all persons`() {
        val person1 = Person("f1", "l1")
        val person2 = Person("f2", "l2")
        val person3 = Person("f3", "l3")

        repo.createPerson(person1)
        repo.createPerson(person2)
        repo.createPerson(person3)

        repo.getPersons() shouldContainAll listOf(person1, person2, person3)
    }
}
