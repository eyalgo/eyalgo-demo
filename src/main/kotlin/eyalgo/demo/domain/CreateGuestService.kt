package eyalgo.demo.domain

import eyalgo.demo.domain.model.Guest
import eyalgo.demo.ports.GuestsRepository
import jakarta.inject.Singleton
import java.util.UUID


@Singleton
class CreateGuestService(private val guestsRepository: GuestsRepository) {
    fun createGuest(messageId: UUID, guest: Guest): Long =
        guestsRepository.createGuest(messageId, guest)
}
