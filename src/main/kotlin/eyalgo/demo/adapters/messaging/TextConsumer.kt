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
class TextConsumer {
    private val log = LoggerFactory.getLogger(TextConsumer::class.java)
    @Queue(value = "queue_text")
    fun receive(@MessageBody body: String) {
        log.info("receive: $body")
    }
}
