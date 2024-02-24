package eyalgo.demo.adapters.data.exposed

import eyalgo.demo.domain.model.Guest
import eyalgo.demo.ports.GuestsRepository
import io.micronaut.context.annotation.Requires
import jakarta.inject.Singleton
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

@Singleton
@Requires(env = ["exposed"])
class GuestsRepositoryImpl: GuestsRepository {

    object Guests : LongIdTable("guests") {
        val firstName: Column<String> = varchar("first_name", length = 100)
        val lastName: Column<String> = varchar("last_name", length = 100)
    }

    override fun createGuest(guest: Guest): Long = transaction {
            addLogger(StdOutSqlLogger)
            Guests.insertAndGetId {
                it[firstName] = guest.firstName
                it[lastName] = guest.lastName
            }.value
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
