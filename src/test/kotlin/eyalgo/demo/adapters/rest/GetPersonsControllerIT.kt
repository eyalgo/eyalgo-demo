package eyalgo.demo.adapters.rest

import eyalgo.demo.teststrategies.ExposedIntegrationTest
import eyalgo.demo.As
import eyalgo.demo.adapters.data.exposed.PersonRepositoryImpl
import eyalgo.demo.domain.model.Person
import eyalgo.demo.containers.ContainerPostgres
import eyalgo.demo.ports.PersonRepository
import io.kotest.matchers.shouldBe
import io.micronaut.runtime.server.EmbeddedServer
import io.micronaut.test.extensions.testresources.annotation.TestResourcesProperties
import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import jakarta.inject.Inject
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExposedIntegrationTest
@TestResourcesProperties(providers = [ContainerPostgres::class])
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
        } Extract {
            body() As Array<PersonDTO>::class.java
        } shouldBe arrayOf(
            PersonDTO("firstName-1", "lastName-1"),
            PersonDTO("firstName-2", "lastName-2")
        )
    }

    @Test
    fun `verify get person`() {
        val id: Long = repo.createPerson(Person("Eyal", "Golan"))

        Given {
            baseUri(server.url.toString())
        } When {
            get("/persons/$id")
        } Then {
            statusCode(200)
            contentType("application/json")
        } Extract {
            body() As PersonDTO::class.java
        } shouldBe PersonDTO("Eyal", "Golan")
    }
}
