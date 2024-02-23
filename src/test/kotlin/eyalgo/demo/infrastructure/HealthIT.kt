package eyalgo.demo.infrastructure

import eyalgo.demo.teststrategies.ExposedIntegrationTest
import eyalgo.demo.containers.ContainerPostgres
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.optional.shouldBePresent
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import io.micronaut.context.annotation.Property
import io.micronaut.http.HttpRequest.GET
import io.micronaut.http.HttpStatus
import io.micronaut.http.HttpStatus.OK
import io.micronaut.http.HttpStatus.SERVICE_UNAVAILABLE
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.runtime.server.EmbeddedServer
import io.micronaut.test.extensions.testresources.annotation.TestResourcesProperties
import jakarta.inject.Inject
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


@ExposedIntegrationTest
@TestResourcesProperties(providers = [ContainerPostgres::class])
class HealthIT {
    @Inject
    private lateinit var server: EmbeddedServer

    @field:Client("/")
    private lateinit var client: HttpClient

    @BeforeEach
    fun setUp() {
        server.start()
        client = server.applicationContext.createBean(HttpClient::class.java, server.url)
    }

    @Test
    @Property(name = "endpoints.health.disk-space.threshold", value = "111111")
    fun `health endpoint is exposed`() {
        client.toBlocking().retrieve(GET<Any>("/health"), HttpStatus::class.java) shouldBe OK
    }

    @Test
    @Property(name = "endpoints.health.disk-space.threshold", value = "999999999999999999")
    fun `show server is down because of health`() {
        val exception = shouldThrow<HttpClientResponseException> {
            client.toBlocking().retrieve(GET<Any>("/health"))
        }
        exception.status shouldBe SERVICE_UNAVAILABLE
        exception.response.body shouldBePresent {
            it as String shouldContain "DOWN"
        }
    }
}
