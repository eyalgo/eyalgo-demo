package eyalgo.demo.infrastructure.authentication

import com.nimbusds.jwt.JWTParser
import com.nimbusds.jwt.SignedJWT
import eyalgo.demo.SimpleIntegrationTest
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.optional.shouldBePresent
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.types.shouldBeTypeOf
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpRequest.GET
import io.micronaut.http.HttpRequest.POST
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus.OK
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.HttpStatus.UNAUTHORIZED
import io.micronaut.http.MediaType.TEXT_PLAIN
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.runtime.server.EmbeddedServer
import io.micronaut.security.authentication.UsernamePasswordCredentials
import io.micronaut.security.token.render.BearerAccessRefreshToken
import jakarta.inject.Inject
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@SimpleIntegrationTest
class SecuredControllerIT {

    @field:Client("/")
    lateinit var client: HttpClient

    @Inject
    private lateinit var server: EmbeddedServer

    @BeforeEach
    fun setUp() {
        server.start()
        client = server.applicationContext.createBean(HttpClient::class.java, server.url)
    }

    @Test
    fun `should throw unauthorized exception`() {
        val exception = shouldThrow<HttpClientResponseException> {
            val requestWithoutAuthorization = GET<String>("/secured")
                .accept(TEXT_PLAIN)
            client.toBlocking()
                .exchange(requestWithoutAuthorization, String::class.java)
        }
        exception.status shouldBe UNAUTHORIZED
    }

    @Test
    fun `issue a jwt token and use it`() {
        val accessToken = retrieveAccessToken()

        val requestWithAuthorization = GET<String>("/secured")
            .accept(TEXT_PLAIN)
            .bearerAuth(accessToken)

        val response = client.toBlocking()
            .exchange(requestWithAuthorization, String::class.java)
        response.status shouldBe OK
        response.body shouldBePresent { it shouldBe "Hello, sherlock!" }
    }

    private fun retrieveAccessToken(): String {
        val credentials = UsernamePasswordCredentials("sherlock", "password")
        val request: HttpRequest<*> = POST("/login", credentials)

        val tokenResponse: HttpResponse<BearerAccessRefreshToken> =
            client.toBlocking().exchange(request, BearerAccessRefreshToken::class.java)
        tokenResponse.status shouldBe OK

        val bearerAccessToken: BearerAccessRefreshToken = tokenResponse.body()
        bearerAccessToken.username shouldBe "sherlock"
        bearerAccessToken.accessToken shouldNotBe null
        JWTParser.parse(bearerAccessToken.accessToken).shouldBeTypeOf<SignedJWT>()

        val accessToken: String = bearerAccessToken.accessToken
        return accessToken
    }
}
