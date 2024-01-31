package eyalgo.demo

import io.kotest.matchers.shouldBe
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Test
import jakarta.inject.Inject
import reactor.core.publisher.Mono


@MicronautTest
    class HelloClientTest {

    @Inject
    lateinit var client: HelloClient

    @Test
    fun testHelloWorldResponse() {
        Mono.from(client.hello()).block() shouldBe "Hello World"
    }
}
