package eyalgo.demo.infrastructure.authentication

import io.micronaut.context.annotation.Replaces
import io.micronaut.context.annotation.Requires
import io.micronaut.http.HttpRequest
import io.micronaut.security.authentication.Authentication
import io.micronaut.security.rules.SecuredAnnotationRule
import io.micronaut.security.rules.SecurityRuleResult
import io.micronaut.security.rules.SecurityRuleResult.ALLOWED
import io.micronaut.security.token.RolesFinder
import jakarta.inject.Singleton
import org.reactivestreams.Publisher
import reactor.core.publisher.Mono

@Replaces(SecuredAnnotationRule::class)
@Singleton
@Requires(notEnv =["secured"])
class AllAllowedAnnotationRuleReplacement(rolesFinder: RolesFinder): SecuredAnnotationRule(rolesFinder) {
    override fun check(request: HttpRequest<*>?, authentication: Authentication?): Publisher<SecurityRuleResult> =
        Mono.just(ALLOWED)
}
