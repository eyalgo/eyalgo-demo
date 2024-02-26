package eyalgo.demo.adapters.data.exposed

import eyalgo.demo.domain.model.Guest
import eyalgo.demo.ports.GuestsRepository
import io.micronaut.context.annotation.Requires
import jakarta.inject.Singleton
import java.util.UUID
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

@Singleton
@Requires(env = ["exposed"])
class GuestsRepositoryImpl: GuestsRepository {

    override fun createGuest(messageId: UUID, guest: Guest): Long = transaction {
            addLogger(StdOutSqlLogger)
        val guestId = Guests.insertAndGetId {
            it[firstName] = guest.firstName
            it[lastName] = guest.lastName
        }.value

        MessageIdToGuestId.insert {
            it[this.messageId] = messageId
            it[this.guestId] = guestId
        }
        guestId
    }

    override fun getGuest(id: Long): Guest = transaction {
        addLogger(StdOutSqlLogger)
        Guests.selectAll()
            .where { Guests.id eq id }
            .map { Guest(it[Guests.firstName], it[Guests.lastName]) }
            .single()
    }

    override fun getGuests(): List<Guest> = transaction {
        Guests.selectAll()
            .map { Guest(it[Guests.firstName], it[Guests.lastName]) }
    }
}
