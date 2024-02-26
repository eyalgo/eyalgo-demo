package eyalgo.demo.adapters.messaging

import eyalgo.demo.domain.CreateGuestService
import eyalgo.demo.domain.model.Guest
import io.micronaut.context.annotation.Infrastructure
import io.micronaut.context.annotation.Requires
import io.micronaut.jms.activemq.classic.configuration.ActiveMqClassicConfiguration.CONNECTION_FACTORY_BEAN_NAME
import io.micronaut.jms.annotations.JMSListener
import io.micronaut.jms.annotations.Queue
import io.micronaut.messaging.annotation.MessageBody

@Infrastructure
@JMSListener(CONNECTION_FACTORY_BEAN_NAME)
@Requires(env = ["messaging"])
class GuestConsumer(private val service: CreateGuestService) {

    @Queue(value = "guests")
    fun receive(@MessageBody guestMessage: GuestCreateMessage) =
        service.createGuest(guestMessage.messageId, Guest(guestMessage.firstName, guestMessage.lastName))
}
