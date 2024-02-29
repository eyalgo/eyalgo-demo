package eyalgo.demo.domain

import eyalgo.demo.domain.model.Guest
import eyalgo.demo.ports.outbound.GuestsRepository
import eyalgo.demo.ports.inbound.AddGuests
import jakarta.inject.Singleton
import java.util.UUID


@Singleton
class CreateGuestService(private val repository: GuestsRepository): AddGuests {
    fun createGuest(messageId: UUID, guest: Guest): Long =
        repository.createGuest(messageId, guest)

    override fun add(guest: Guest): Long =
        repository.createGuest(guest)
}
