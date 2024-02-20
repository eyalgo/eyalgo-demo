package eyalgo.demo.adapters.rest

import eyalgo.demo.IntegrationTest
import eyalgo.demo.matchers.Is
import eyalgo.demo.adapters.data.exposed.PersonRepositoryImpl
import eyalgo.demo.domain.model.Person
import eyalgo.demo.infrastructure.MySQLForTests
import eyalgo.demo.ports.PersonRepository
import io.micronaut.runtime.server.EmbeddedServer
import io.micronaut.test.extensions.testresources.annotation.TestResourcesProperties
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import jakarta.inject.Inject
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

    @BeforeEach
    fun setUp() {
        if (!server.isRunning) server.start()
        transaction {
            PersonRepositoryImpl.Persons.deleteAll()
        }
    }

    @Test
    fun `verify getAll`() {
        Given {
            baseUri(server.url.toString())
        } When {
            get("/persons")
        } Then {
            statusCode(200)
            body(Is("[{\"firstName\":\"firstName-1\",\"lastName\":\"lastName-1\"},{\"firstName\":\"firstName-2\",\"lastName\":\"lastName-2\"}]"))
        }
    }

    @Test
    fun `verify get person`() {
        val id = repo.createPerson(Person("Eyal", "Golan"))
        Given {
            baseUri(server.url.toString())
        } When {
            get("/persons/$id")
        } Then {
            statusCode(200)
//            body(Is("{\"firstName\":\"Eyal\",\"lastName\":\"Golan\"}"))
        }

        //private val objectMapper: ObjectMapper
//        objectMapper.readValue(rsp, PersonDTO::class.java) shouldBe PersonDTO("Eyal", "Golan")
    }
}
