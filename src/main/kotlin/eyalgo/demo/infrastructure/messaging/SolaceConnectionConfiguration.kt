package eyalgo.demo.infrastructure.messaging

import io.micronaut.context.annotation.ConfigurationProperties

@ConfigurationProperties("solace")
class SolaceConnectionConfiguration {
    var url: String = ""
    var user: String = ""
    var password: String = ""
}
