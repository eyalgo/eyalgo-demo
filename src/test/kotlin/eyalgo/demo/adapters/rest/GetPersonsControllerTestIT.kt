package eyalgo.demo.adapters.rest

import eyalgo.demo.domain.model.Person
import eyalgo.demo.ports.PersonRepository
import io.kotest.matchers.shouldBe
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.runtime.server.EmbeddedServer
import io.micronaut.serde.ObjectMapper
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Test

@MicronautTest
class GetPersonsControllerTestIT(private val mapper: ObjectMapper) {
    @Inject
    lateinit var repo: PersonRepository

    @Inject
    private lateinit var server: EmbeddedServer

    @Inject
    @field:Client("/")
    lateinit var client: HttpClient

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

        mapper.readValue(rsp, PersonDTO::class.java) shouldBe PersonDTO("Eyal", "Golan")
    }
}
