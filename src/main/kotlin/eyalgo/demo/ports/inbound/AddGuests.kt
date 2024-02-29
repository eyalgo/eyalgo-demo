package eyalgo.demo.ports.inbound

import eyalgo.demo.domain.model.Guest

interface AddGuests {
    fun add(guest: Guest): Long
}
