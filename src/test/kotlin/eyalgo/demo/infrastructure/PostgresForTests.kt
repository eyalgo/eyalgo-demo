package eyalgo.demo.infrastructure

import io.micronaut.core.annotation.ReflectiveAccess
import io.micronaut.test.extensions.testresources.TestResourcesPropertyProvider
import org.testcontainers.containers.PostgreSQLContainer

@ReflectiveAccess
class PostgresForTests: TestResourcesPropertyProvider {
    private val postgres = PostgreSQLContainer("postgres:15.2-alpine")
    init {
        println("=================== PostgresForTests init =================== ")

        if (!postgres.isRunning()) {
            println("postgres is not running, starting it now...")
            postgres.start()
        }
    }

    override fun provide(testProperties: MutableMap<String, Any>): MutableMap<String, String> {
        println("=================== PostgresForTests provide =================== ")
        return mutableMapOf(
            "datasources.default.driver-class-name" to "org.postgresql.Driver",
            "datasources.default.url" to postgres.jdbcUrl,
            "datasources.default.username" to postgres.username,
            "datasources.default.password" to postgres.password,
            "datasources.default.driverClassName" to "org.postgresql.Driver"
        )
    }
}