package eyalgo.demo

import eyalgo.demo.matchers.Is
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.runtime.server.EmbeddedServer
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import io.restassured.specification.RequestSpecification
import jakarta.inject.Inject
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@MicronautTest
class HelloControllerIT {

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
    fun helloWorld1(spec: RequestSpecification) {
        spec
            .`when`().get("/hello/1")
            .then().statusCode(200)
            .body(Is("Hello World 1"))
    }

//    @Test
//    fun helloWorld1() {
//        When {
//            get("/hello/1")
//        } Then {
//            statusCode(200)
//            body(Is("Hello World 1"))
//        }
//    }
}
