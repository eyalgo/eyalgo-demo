package eyalgo.demo.adapters.rest

import eyalgo.demo.Is
import eyalgo.demo.domain.model.Guest
import eyalgo.demo.ports.inbound.AddGuests
import io.micronaut.runtime.server.EmbeddedServer
import io.micronaut.test.annotation.MockBean
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import io.restassured.http.ContentType.JSON
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import jakarta.inject.Inject
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS

@TestInstance(PER_CLASS)
@MicronautTest(
    startApplication = false,
    environments = ["integrationTest"]
)
class AddGuestControllerIT {
    @Inject
    private lateinit var server: EmbeddedServer

    private val stubbedGuestId = 19L
    @MockBean(AddGuests::class)
    fun addGuests(): AddGuests = object : AddGuests {
        override fun add(guest: Guest): Long = stubbedGuestId
    }

    @BeforeEach
    fun setUp() {
        if (!server.isRunning) server.start()
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
            body (Is(stubbedGuestId.toString()))
        }
    }
}
