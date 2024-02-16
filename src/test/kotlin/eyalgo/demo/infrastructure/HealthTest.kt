package eyalgo.demo.infrastructure

import io.kotest.matchers.shouldBe
import io.micronaut.http.HttpRequest.GET
import io.micronaut.http.HttpStatus
import io.micronaut.http.HttpStatus.OK
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Test

@MicronautTest
class HealthTest(@Client("/") val client: HttpClient) {
    @Test
    fun `health endpoint is exposed`() {
        client.toBlocking().retrieve(GET<Any>("/health"), HttpStatus::class.java) shouldBe OK
    }
}
