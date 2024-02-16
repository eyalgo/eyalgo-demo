package eyalgo.demo.adapters.rest

import eyalgo.demo.IntegrationTest
import eyalgo.demo.adapters.data.exposed.PersonRepositoryImpl
import eyalgo.demo.infrastructure.PostgresForTests
import eyalgo.demo.domain.model.Person
import eyalgo.demo.ports.PersonRepository
import io.micronaut.context.ApplicationContext
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.runtime.server.EmbeddedServer
import io.micronaut.test.extensions.testresources.annotation.TestResourcesProperties
import jakarta.inject.Inject
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@IntegrationTest
@TestResourcesProperties(providers = [PostgresForTests::class])
class GetPersonsControllerIT {
    @Inject
    lateinit var repo: PersonRepository

    @Inject
    private lateinit var context: ApplicationContext

    @field:Client("/")
    lateinit var client: HttpClient

    @BeforeEach
    fun setUp() {
        val server = context.getBean(EmbeddedServer::class.java)
        server.start()
        client = server.applicationContext.createBean(HttpClient::class.java, server.url)
        transaction {
            PersonRepositoryImpl.Persons.deleteAll()
        }
    }

    @Test
    fun `verify getAll`() {
        val rsp: String = client.toBlocking()
            .retrieve("/persons")
        println(rsp)
    }

    @Test
    fun `verify get person`() {
        val id = repo.createPerson(Person("Eyal", "Golan"))

        val rsp: String = client.toBlocking()
            .retrieve("/persons/$id")

        //private val objectMapper: ObjectMapper
//        objectMapper.readValue(rsp, PersonDTO::class.java) shouldBe PersonDTO("Eyal", "Golan")
    }
}
