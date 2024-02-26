package eyalgo.demo.adapters.data.exposed

import java.util.UUID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

class Tables

object Guests : LongIdTable("guests") {
    val firstName: Column<String> = varchar("first_name", length = 100)
    val lastName: Column<String> = varchar("last_name", length = 100)
}

object MessageIdToGuestId : Table("message_id_to_guest_id") {
    val messageId: Column<UUID> = uuid("message_id")
    val guestId: Column<Long> = long("guest_id")

    override val primaryKey = PrimaryKey(messageId)
}
