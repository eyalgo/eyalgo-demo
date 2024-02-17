package eyalgo.demo.infrastructure.authentication

import io.micronaut.http.MediaType.TEXT_PLAIN
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Produces
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule.IS_AUTHENTICATED
import java.security.Principal

@Secured(IS_AUTHENTICATED)
@Controller("/secured")
class SecuredController {

    @Produces(TEXT_PLAIN)
    @Get
    fun index(principal: Principal): String = "Hello, ${principal.name}!"
}
