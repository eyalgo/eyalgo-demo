package eyalgo.demo.infrastructure

import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest

@MicronautTest
class FlywayEndpointIT(@field:Client("/") val client: HttpClient) {

}