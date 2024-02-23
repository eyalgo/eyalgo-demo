package eyalgo.demo.containers

import io.micronaut.context.DefaultApplicationContextBuilder
import io.micronaut.core.annotation.ReflectiveAccess
import io.micronaut.test.extensions.testresources.TestResourcesPropertyProvider
import org.testcontainers.containers.MySQLContainer

@ReflectiveAccess
class ContainerMySQL: TestResourcesPropertyProvider, DefaultApplicationContextBuilder() {
    private val mysql = MySQLContainer("mysql:8.0.33")

    init {
        println("=================== ContainerMySQL init =================== ")

        if (!mysql.isRunning()) {
            println("mysql is not running, starting it now...")
            mysql.start()
        }
    }

    override fun provide(testProperties: MutableMap<String, Any>): MutableMap<String, String> {
        println("=================== ContainerMySQL provide =================== ")
        return mutableMapOf(
            "flyway.datasources.mysql.enabled" to "true",
            "datasources.mysql.url" to mysql.jdbcUrl,
            "datasources.mysql.username" to mysql.username,
            "datasources.mysql.password" to mysql.password,
            "datasources.mysql.dialect" to "MYSQL",
            "datasources.mysql.driverClassName" to "com.mysql.cj.jdbc.Driver"
        )
    }
}
