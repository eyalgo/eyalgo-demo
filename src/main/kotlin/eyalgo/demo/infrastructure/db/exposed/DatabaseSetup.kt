package eyalgo.demo.infrastructure.db.exposed

import io.micronaut.context.annotation.Context
import io.micronaut.context.annotation.Infrastructure
import org.jetbrains.exposed.sql.Database

@Context
@Infrastructure
class DatabaseSetup {
    init {
        println("----------------- Database Setup -----------------")
        Database.connect(
            "jdbc:h2:mem:test;SCHEMA=PUBLIC;DB_CLOSE_DELAY=-1",
            driver = "org.h2.Driver",
            user = "sa",
            password = ""
        )
    }
}
