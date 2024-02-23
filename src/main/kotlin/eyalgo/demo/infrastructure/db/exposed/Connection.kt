package eyalgo.demo.infrastructure.db.exposed

import io.micronaut.configuration.jdbc.hikari.DatasourceConfiguration
import io.micronaut.context.annotation.Context
import io.micronaut.context.annotation.EachBean
import io.micronaut.context.annotation.Infrastructure
import io.micronaut.context.annotation.Requires
import org.jetbrains.exposed.sql.Database

@Context
@Infrastructure
@Requires(env = ["exposed"])
@EachBean(DatasourceConfiguration::class)
data class Connection(private val configuration: DatasourceConfiguration) {
    init {
        println("------------------- Exposed Connection Setup -------------------")
        Database.connect(
            configuration.url,
            driver = configuration.driverClassName,
            user = configuration.username,
            password = configuration.password
        )
    }
}
