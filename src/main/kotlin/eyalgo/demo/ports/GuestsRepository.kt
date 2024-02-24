package eyalgo.demo.ports

import eyalgo.demo.domain.model.Guest

interface GuestsRepository {
    fun createGuest(guest: Guest): Long
    fun getGuest(id: Long): Guest

    fun getGuests(): List<Guest>
}
