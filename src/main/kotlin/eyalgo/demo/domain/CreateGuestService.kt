package eyalgo.demo.domain

import eyalgo.demo.domain.model.Guest
import eyalgo.demo.ports.GuestsRepository
import jakarta.inject.Singleton


@Singleton
class CreateGuestService(private val guestsRepository: GuestsRepository) {
    fun createGuest(guest: Guest): Long = guestsRepository.createGuest(guest)
}
