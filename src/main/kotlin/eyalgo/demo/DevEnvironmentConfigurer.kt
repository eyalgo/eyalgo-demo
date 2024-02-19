package eyalgo.demo

import io.micronaut.context.ApplicationContextConfigurer
import io.micronaut.context.annotation.ContextConfigurer

@ContextConfigurer
class DevEnvironmentConfigurer: ApplicationContextConfigurer {
    override fun configure(builder: io.micronaut.context.ApplicationContextBuilder) {
        builder.defaultEnvironments("exposed")
    }
}
