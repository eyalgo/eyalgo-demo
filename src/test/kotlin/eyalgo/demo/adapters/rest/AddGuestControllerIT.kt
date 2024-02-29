package eyalgo.demo.adapters.rest

import eyalgo.demo.Is
import eyalgo.demo.adapters.data.exposed.Guests
import eyalgo.demo.teststrategies.ExposedH2IntegrationTest
import eyalgo.demo.teststrategies.STUBBED_GUEST_ID
import io.micronaut.runtime.server.EmbeddedServer
import io.restassured.http.ContentType.JSON
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import jakarta.inject.Inject
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExposedH2IntegrationTest
class AddGuestControllerIT {
    @Inject
    private lateinit var server: EmbeddedServer

    @BeforeEach
    fun setUp() {
        if (!server.isRunning) server.start()
        transaction { Guests.deleteAll() }
    }

    @Test
    fun `add guest controller works`() {
        Given {
            baseUri(server.url.toString())
            contentType(JSON)
            body(GuestDTO("Elvis", "Presley"))
        } When {
            post("/guests")
        } Then {
            statusCode(201)
            body (Is(STUBBED_GUEST_ID.toString()))
        }
    }
}
