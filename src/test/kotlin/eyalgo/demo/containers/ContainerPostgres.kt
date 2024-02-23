package eyalgo.demo.containers

import io.micronaut.context.DefaultApplicationContextBuilder
import io.micronaut.core.annotation.ReflectiveAccess
import io.micronaut.test.extensions.testresources.TestResourcesPropertyProvider
import org.testcontainers.containers.PostgreSQLContainer

@ReflectiveAccess
class ContainerPostgres: TestResourcesPropertyProvider, DefaultApplicationContextBuilder() {
    private val postgres = PostgreSQLContainer("postgres:15.2-alpine")

    init {
        println("=================== ContainerPostgres init =================== ")

        if (!postgres.isRunning()) {
            println("postgres is not running, starting it now...")
            postgres.start()
        }
    }

    override fun provide(testProperties: MutableMap<String, Any>): MutableMap<String, String> {
        println("=================== ContainerPostgres provide =================== ")
        return mutableMapOf(
            "flyway.datasources.postgres.enabled" to "true",
            "datasources.postgres.url" to postgres.jdbcUrl,
            "datasources.postgres.username" to postgres.username,
            "datasources.postgres.password" to postgres.password,
            "datasources.postgres.dialect" to "POSTGRES",
            "datasources.postgres.driverClassName" to "org.postgresql.Driver"
        )
    }
}
