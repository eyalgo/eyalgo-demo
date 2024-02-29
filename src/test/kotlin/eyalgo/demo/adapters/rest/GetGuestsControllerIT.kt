package eyalgo.demo.adapters.rest

import eyalgo.demo.teststrategies.ExposedIntegrationTest
import eyalgo.demo.As
import eyalgo.demo.containers.ContainerMySQL
import eyalgo.demo.domain.model.Guest
import eyalgo.demo.ports.GuestsRepository
import io.kotest.matchers.shouldBe
import io.micronaut.runtime.server.EmbeddedServer
import io.micronaut.test.extensions.testresources.annotation.TestResourcesProperties
import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import jakarta.inject.Inject
import java.util.UUID.randomUUID
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExposedIntegrationTest
@TestResourcesProperties(providers = [ContainerMySQL::class])
class GetGuestsControllerIT {
    @Inject
    lateinit var repo: GuestsRepository

    @Inject
    private lateinit var server: EmbeddedServer

    @BeforeEach
    fun setUp() {
        if (!server.isRunning) server.start()
    }

    @Test
    fun `verify getAll`() {
        Given {
            baseUri(server.url.toString())
        } When {
            get("/guests")
        } Then {
            statusCode(200)
        } Extract {
            body() As Array<GuestDTO>::class.java
        } shouldBe arrayOf(
            GuestDTO("firstName-1", "lastName-1"),
            GuestDTO("firstName-2", "lastName-2")
        )
    }

    @Test
    fun `verify get guest`() {
        val id: Long = repo.createGuest(randomUUID(), Guest("Eyal", "Golan"))

        Given {
            baseUri(server.url.toString())
        } When {
            get("/guests/$id")
        } Then {
            statusCode(200)
            contentType("application/json")
        } Extract {
            body() As GuestDTO::class.java
        } shouldBe GuestDTO("Eyal", "Golan")
    }
}
