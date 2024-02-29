package eyalgo.demo.infrastructure

import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

@MicronautTest
@Disabled("There are issues with the serialization of the flyway endpoint")
class FlywayEndpointIT(@field:Client("/") val client: HttpClient) {
    /**
     * See: https://guides.micronaut.io/latest/micronaut-flyway-gradle-kotlin.html
     */
    @Test
    fun `verify flyway endpoint`() {
    }
}
