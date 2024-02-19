package eyalgo.demo.infrastructure

import io.micronaut.http.HttpStatus.OK
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

@MicronautTest
class FlywayEndpointIT(@field:Client("/") val client: HttpClient) {
    @Test
    @Disabled("There are issues with the serialisation of the flyway endpoint")
    fun `verify flyway endpoint`() {
        When {
            get("/flyway")
        } Then {
            statusCode(OK.code)
        }
    }
}
