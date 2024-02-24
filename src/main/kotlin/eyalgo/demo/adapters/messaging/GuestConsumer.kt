package eyalgo.demo.adapters.messaging

import io.micronaut.context.annotation.Infrastructure
import io.micronaut.context.annotation.Requires
import io.micronaut.jms.activemq.classic.configuration.ActiveMqClassicConfiguration.CONNECTION_FACTORY_BEAN_NAME
import io.micronaut.jms.annotations.JMSListener
import io.micronaut.jms.annotations.Queue
import io.micronaut.messaging.annotation.MessageBody
import org.slf4j.LoggerFactory

@Infrastructure
@JMSListener(CONNECTION_FACTORY_BEAN_NAME)
@Requires(env = ["messaging"])
class GuestConsumer {
    companion object {
        private val log = LoggerFactory.getLogger(GuestConsumer::class.java.name)
    }

    @Queue(value = "guests")
    fun receive(@MessageBody guest: GuestCreateMessage) {
        log.info("receive: $guest")
    }
}
