package eyalgo.demo

import eyalgo.demo.matchers.Is
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.junit.jupiter.api.Test

@MicronautTest
class HelloControllerTest {

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
