package eyalgo.demo

import io.micronaut.runtime.server.EmbeddedServer
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.junit.jupiter.api.Test

@MicronautTest
class HelloControllerIT(private val server: EmbeddedServer) {

    @Test
    fun helloWorld1() {
        Given {
            baseUri(server.url.toString())
        } When {
            get("/hello/1")
        } Then {
            statusCode(200)
            body(Is("Hello World 1"))
        }
    }
}
