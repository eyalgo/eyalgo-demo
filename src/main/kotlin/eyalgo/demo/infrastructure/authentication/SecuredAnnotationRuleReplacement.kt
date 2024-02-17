package eyalgo.demo.infrastructure.authentication

import io.micronaut.context.annotation.Replaces
import io.micronaut.http.HttpAttributes
import io.micronaut.http.HttpRequest
import io.micronaut.security.annotation.Secured
import io.micronaut.security.authentication.Authentication
import io.micronaut.security.rules.SecuredAnnotationRule
import io.micronaut.security.rules.SecurityRuleResult
import io.micronaut.security.rules.SecurityRuleResult.ALLOWED
import io.micronaut.security.token.RolesFinder
import io.micronaut.web.router.MethodBasedRouteMatch
import io.micronaut.web.router.RouteMatch
import jakarta.inject.Singleton
import org.reactivestreams.Publisher
import reactor.core.publisher.Mono

@Replaces(SecuredAnnotationRule::class)
@Singleton
class SecuredAnnotationRuleReplacement(rolesFinder: RolesFinder): SecuredAnnotationRule(rolesFinder){
    override fun check(request: HttpRequest<*>?, authentication: Authentication?): Publisher<SecurityRuleResult> {
        val routeMatch = request!!.getAttribute(
            HttpAttributes.ROUTE_MATCH,
            RouteMatch::class.java
        ).orElse(null)
        if (routeMatch is MethodBasedRouteMatch<*, *>) {
            return Mono.just(
                if (routeMatch.hasAnnotation(Secured::class.java)) {
                    return super.check(request, authentication)
                } else {
                    ALLOWED
                }
            )
        }
        return super.check(request, authentication)
    }
}
