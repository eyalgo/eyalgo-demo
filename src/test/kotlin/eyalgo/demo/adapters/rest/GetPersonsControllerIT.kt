package eyalgo.demo.adapters.rest

import eyalgo.demo.adapters.data.exposed.PersonRepositoryImpl
import eyalgo.demo.domain.model.Person
import eyalgo.demo.ports.PersonRepository
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.runtime.server.EmbeddedServer
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import io.micronaut.test.support.TestPropertyProvider
import jakarta.inject.Inject
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS

@MicronautTest(environments = ["h2"])
@TestInstance(PER_CLASS)
class GetPersonsControllerIT: TestPropertyProvider {
    @Inject
    lateinit var repo: PersonRepository

    @Inject
    private lateinit var server: EmbeddedServer

    @Inject
    @field:Client("/")
    lateinit var client: HttpClient

    @BeforeEach
    fun setUp() {
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

//        ObjectMapper().readValue(rsp, PersonDTO::class.java) shouldBe PersonDTO("Eyal", "Golan")
    }

    override fun getProperties(): MutableMap<String, String> {
        println("************************ GetPersonsControllerIT.getProperties() ************************")
        return mutableMapOf(
            "datasources.default.url" to "jdbc:h2:mem:test;SCHEMA=PUBLIC;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=TRUE",
            "datasources.default.username" to "sa",
            "datasources.default.password" to "",
            "datasources.default.driverClassName" to "org.h2.Driver"
        )
    }
}
