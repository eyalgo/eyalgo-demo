package eyalgo.demo

import eyalgo.demo.matchers.Is
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.junit.jupiter.api.Test

@MicronautTest
class HelloControllerTest(@field:Client("/") val client: HttpClient) {

    @Test
    fun helloWorld1() {
        When {
            get("/hello/1")
        } Then {
            statusCode(200)
            body(Is("Hello World 1"))
        }
    }
}
