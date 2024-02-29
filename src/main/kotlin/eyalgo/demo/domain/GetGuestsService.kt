package eyalgo.demo.domain

import eyalgo.demo.domain.model.Guest
import eyalgo.demo.ports.outbound.GuestsRepository
import jakarta.inject.Singleton
import org.slf4j.LoggerFactory

@Singleton
class GetGuestsService(private val repository: GuestsRepository) {
    companion object {
        private val log = LoggerFactory.getLogger(GetGuestsService::class.java.name)
    }

    fun getGuests(): List<Guest> {
        log.info("getting guests")
        return listOf(
            Guest("firstName-1", "lastName-1"),
            Guest("firstName-2", "lastName-2")
        )
    }

    fun getGuest(id: Long): Guest = repository.getGuest(id)
}
