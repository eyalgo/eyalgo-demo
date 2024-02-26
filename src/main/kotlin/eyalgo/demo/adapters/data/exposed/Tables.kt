package eyalgo.demo.adapters.data.exposed

import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Column

class Tables

object Guests : LongIdTable("guests") {
    val firstName: Column<String> = varchar("first_name", length = 100)
    val lastName: Column<String> = varchar("last_name", length = 100)
}
