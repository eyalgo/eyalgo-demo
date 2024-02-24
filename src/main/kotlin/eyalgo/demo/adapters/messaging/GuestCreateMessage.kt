package eyalgo.demo.adapters.messaging

import io.micronaut.serde.annotation.Serdeable
import java.util.UUID

@Serdeable
data class GuestCreateMessage(val messageId: UUID, val firstName: String, val lastName: String)
