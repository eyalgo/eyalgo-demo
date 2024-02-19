package eyalgo.demo.infrastructure

import io.micronaut.context.DefaultApplicationContextBuilder
import io.micronaut.core.annotation.ReflectiveAccess
import io.micronaut.test.extensions.testresources.TestResourcesPropertyProvider
import org.testcontainers.containers.MySQLContainer

@ReflectiveAccess
class MySQLForTests: TestResourcesPropertyProvider, DefaultApplicationContextBuilder() {
    private val mysql = MySQLContainer("mysql:8.0.33")

    init {
        println("=================== MySQLForTests init =================== ")

        if (!mysql.isRunning()) {
            println("mysql is not running, starting it now...")
            mysql.start()
        }
    }

    override fun provide(testProperties: MutableMap<String, Any>): MutableMap<String, String> {
        println("=================== MySQLForTests provide =================== ")
        return mutableMapOf(
            "datasources.default.url" to mysql.jdbcUrl,
            "datasources.default.username" to mysql.username,
            "datasources.default.password" to mysql.password,
            "datasources.default.driverClassName" to "com.mysql.cj.jdbc.Driver"
        )
    }
}
