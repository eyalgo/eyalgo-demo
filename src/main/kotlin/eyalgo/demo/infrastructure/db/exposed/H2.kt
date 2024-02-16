package eyalgo.demo.infrastructure.db.exposed

import io.micronaut.context.annotation.Context
import io.micronaut.context.annotation.Infrastructure
import io.micronaut.context.annotation.Requires
import org.jetbrains.exposed.sql.Database

@Context
@Infrastructure
@Requires(env = ["h2", "exposed"], notEnv = ["mysql"])
class H2 {
    init {
        println("----------------- H2 (exposed) Setup -----------------")
        Database.connect(
            "jdbc:h2:mem:test;SCHEMA=PUBLIC;DB_CLOSE_DELAY=-1",
            driver = "org.h2.Driver",
            user = "sa",
            password = ""
        )
    }
}
