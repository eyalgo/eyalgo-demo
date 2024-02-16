package eyalgo.demo.infrastructure

import eyalgo.demo.IntegrationTest
import io.kotest.matchers.shouldBe
import io.micronaut.context.ApplicationContext
import io.micronaut.http.HttpRequest.GET
import io.micronaut.http.HttpStatus
import io.micronaut.http.HttpStatus.OK
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.runtime.server.EmbeddedServer
import io.micronaut.test.extensions.testresources.annotation.TestResourcesProperties
import jakarta.inject.Inject
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


@IntegrationTest
@TestResourcesProperties(providers = [PostgresForTests::class])
class HealthIT {
    @Inject
    lateinit var context: ApplicationContext

    @field:Client("/")
    lateinit var client: HttpClient

    @BeforeEach
    fun setUp() {
        val server = context.getBean(EmbeddedServer::class.java)
        server.start()
        client = server.applicationContext.createBean(HttpClient::class.java, server.url)
    }

    @Test
    fun `health endpoint is exposed`() {
        client.toBlocking().retrieve(GET<Any>("/health"), HttpStatus::class.java) shouldBe OK
    }
}