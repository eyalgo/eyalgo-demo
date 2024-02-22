package eyalgo.demo.adapters.messaging

import io.micronaut.context.annotation.Requires
import io.micronaut.jms.activemq.classic.configuration.ActiveMqClassicConfiguration.CONNECTION_FACTORY_BEAN_NAME
import io.micronaut.jms.annotations.JMSProducer
import io.micronaut.jms.annotations.Queue
import io.micronaut.messaging.annotation.MessageBody

@JMSProducer(CONNECTION_FACTORY_BEAN_NAME)
@Requires(env = ["messaging"])
interface TextProducer {

    @Queue("queue_text")
    fun send(@MessageBody body: String)
}
