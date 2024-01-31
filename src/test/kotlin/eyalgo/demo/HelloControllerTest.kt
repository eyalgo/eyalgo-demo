package eyalgo.demo

import io.kotest.matchers.shouldBe
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.runtime.server.EmbeddedServer
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Test

@MicronautTest
class HelloControllerTest {
    @Inject
    private lateinit var server: EmbeddedServer

    @Inject
    @field:Client("/")
    lateinit var client: HttpClient

    @Test
    fun `verify HelloWorld`() {
        val rsp: String = client.toBlocking()
            .retrieve("/hello")

        rsp shouldBe "Hello World"
    }
}
