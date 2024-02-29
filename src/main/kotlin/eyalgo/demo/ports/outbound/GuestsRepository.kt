package eyalgo.demo.ports.outbound

import eyalgo.demo.domain.model.Guest
import java.util.UUID

interface GuestsRepository {
    fun createGuest(messageId: UUID, guest: Guest): Long
    fun getGuest(id: Long): Guest
    fun getGuests(): List<Guest>
    fun createGuest(guest: Guest): Long
}
