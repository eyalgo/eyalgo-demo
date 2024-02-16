package eyalgo.demo.adapters.data.exposed

import eyalgo.demo.domain.model.Person
import eyalgo.demo.infrastructure.PostgresForTests
import eyalgo.demo.ports.PersonRepository
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.shouldBe
import io.micronaut.context.ApplicationContext
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import io.micronaut.test.extensions.testresources.annotation.TestResourcesProperties
import jakarta.inject.Inject
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS

@MicronautTest(
    startApplication = false,
    environments = ["exposed"]
)
@TestInstance(PER_CLASS)
@TestResourcesProperties(providers = [PostgresForTests::class])
class PersonsRepositoryImplIT {
    @Inject
    lateinit var repo: PersonRepository

    @Inject
    lateinit var context: ApplicationContext

    @Test
    fun `create and get a person`() {
        val person = Person("Eyal", "Golan")
        val id = repo.createPerson(person)

        repo.getPerson(id) shouldBe person
    }

    @Test
    fun `check environments`() {
        val environment = context.environment

        environment.activeNames shouldContain "test"
        environment.activeNames shouldContain "exposed"
    }
}
