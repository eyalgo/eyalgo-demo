package eyalgo.demo.adapters.data.exposed

import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.Column

class Tables

object Guests : LongIdTable("guests") {
    val firstName: Column<String> = varchar("first_name", length = 100)
    val lastName: Column<String> = varchar("last_name", length = 100)
}

object MessageIdToGuestId : UUIDTable(
    name = "message_id_to_guest_id",
    columnName = "message_id"
) {
    val guestId: Column<Long> = long("guest_id")
}
