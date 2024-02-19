package eyalgo.demo.adapters.rest

import eyalgo.demo.IntegrationTest
import eyalgo.demo.adapters.data.exposed.PersonRepositoryImpl
import eyalgo.demo.domain.model.Person
import eyalgo.demo.infrastructure.MySQLForTests
import eyalgo.demo.ports.PersonRepository
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.runtime.server.EmbeddedServer
import io.micronaut.test.extensions.testresources.annotation.TestResourcesProperties
import io.restassured.specification.RequestSpecification
import jakarta.inject.Inject
import org.hamcrest.CoreMatchers.`is`
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@IntegrationTest
@TestResourcesProperties(providers = [MySQLForTests::class])
class GetPersonsControllerIT {
    @Inject
    lateinit var repo: PersonRepository

    @Inject
    private lateinit var server: EmbeddedServer

    @field:Client("/")
    private lateinit var client: HttpClient

    @BeforeEach
    fun setUp() {
        server.start()
        client = server.applicationContext.createBean(HttpClient::class.java, server.url)
        transaction {
            PersonRepositoryImpl.Persons.deleteAll()
        }
    }

    @Test
    fun `verify getAll`(spec: RequestSpecification) {
        spec
            .`when`().get("/persons")
            .then().statusCode(200)
            .body(`is`("[{\"firstName\":\"firstName-1\",\"lastName\":\"lastName-1\"},{\"firstName\":\"firstName-2\",\"lastName\":\"lastName-2\"}]"))
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
