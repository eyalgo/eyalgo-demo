package eyalgo.demo.infrastructure.db.exposed

import io.micronaut.context.annotation.Context
import io.micronaut.context.annotation.Infrastructure
import io.micronaut.context.annotation.Requires
import org.jetbrains.exposed.sql.Database

@Context
@Infrastructure
@Requires(env = ["mysql", "exposed"])
class MySQL {
    init {
        println("----------------- MySQL Setup -----------------")
        Database.connect(
            "jdbc:h2:mem:test;SCHEMA=PUBLIC;DB_CLOSE_DELAY=-1",
            driver = "org.h2.Driver",
            user = "sa",
            password = ""
        )
    }
}
