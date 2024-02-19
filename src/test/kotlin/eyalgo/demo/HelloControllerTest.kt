package eyalgo.demo

import eyalgo.demo.infrastructure.MySQLForTests
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.runtime.server.EmbeddedServer
import io.micronaut.test.extensions.testresources.annotation.TestResourcesProperties
import io.restassured.specification.RequestSpecification
import jakarta.inject.Inject
import org.hamcrest.CoreMatchers.`is`
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@IntegrationTest
@TestResourcesProperties(providers = [MySQLForTests::class])
class HelloControllerTest {

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
            .body(`is`("Hello World 1"))
    }
}
