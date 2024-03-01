package eyalgo.demo.usecases

import eyalgo.demo.adapters.data.exposed.Guests
import eyalgo.demo.adapters.rest.GuestDTO
import io.kotest.matchers.shouldBe
import io.micronaut.http.HttpRequest.POST
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus.CREATED
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.runtime.server.EmbeddedServer
import jakarta.inject.Inject
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.BeforeEach


abstract class AbstractUseCase {
    @Inject
    private lateinit var server: EmbeddedServer

    @field:Client("/")
    protected lateinit var client: HttpClient

    @BeforeEach
    fun setUp() {
        server.start()
        client = server.applicationContext.createBean(HttpClient::class.java, server.url)
    }

    protected fun whenAddGuest(guest: GuestDTO): UseCaseSpecification {

        val response = client.toBlocking().exchange(POST("/guests", guest), Long::class.java)
        response.status shouldBe CREATED
        return UseCaseSpecification(
            response = response,
            guest = guest
        )
    }

    protected fun UseCaseSpecification.thenItShouldBePersisted() {
        guest shouldBePersistedWithId response.body.get()
    }

    private infix fun GuestDTO.shouldBePersistedWithId(guestId: Long) {
        val guest = transaction {
            Guests.selectAll()
                .where { Guests.id eq guestId }
                .single()
        }
        guest[Guests.firstName] shouldBe this.firstName
        guest[Guests.lastName] shouldBe this.lastName
    }

    protected data class UseCaseSpecification(val response: HttpResponse<Long>, val guest: GuestDTO)
}
