package eyalgo.demo.infrastructure.db.exposed

import io.micronaut.context.annotation.Context
import io.micronaut.context.annotation.Infrastructure
import io.micronaut.context.annotation.Property
import io.micronaut.context.annotation.Requires
import org.jetbrains.exposed.sql.Database

@Context
@Infrastructure
@Requires(env = ["exposed"])
data class Connection(
    @Property(name = "datasources.default.url") val url: String,
    @Property(name = "datasources.default.username") val username: String,
    @Property(name = "datasources.default.password") val password: String,
    @Property(name = "datasources.default.driverClassName") val driver: String,
) {
    init {
        println("------------------- Exposed Connection Setup -------------------")
        println(this)
        Database.connect(
            url,
            driver = driver,
            user = username,
            password = password
        )
    }
}
