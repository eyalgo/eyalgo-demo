package eyalgo.demo.containers

import io.micronaut.core.annotation.ReflectiveAccess
import io.micronaut.test.extensions.testresources.TestResourcesPropertyProvider
import org.testcontainers.containers.GenericContainer
import org.testcontainers.utility.DockerImageName

@ReflectiveAccess
class ContainerActiveMQ: TestResourcesPropertyProvider {
    companion object {
        private val broker: GenericContainer<*> =
            GenericContainer<Nothing>(DockerImageName.parse("rmohr/activemq"))
                .apply {
                    withCreateContainerCmdModifier { cmd -> cmd.withPlatform("linux/amd64") }
                    withExposedPorts(61616)
                }
    }

    init {
        println("=================== ContainerActiveMQ init =================== ")

        if (!broker.isRunning()) {
            println("mysql is not running, starting it now...")
            broker.start()
        }
    }

    override fun provide(testProperties: MutableMap<String, Any>?): MutableMap<String, String> {
        println("=================== ContainerActiveMQ provide =================== ")
        return mutableMapOf(
            "message-broker.url" to "tcp://${broker.host}:${broker.getMappedPort(61616)}",
            "message-broker.user" to "user",
            "message-broker.password" to "password",
            "micronaut.jms.activemq.classic.connection-string" to "tcp://${broker.host}:${broker.getMappedPort(61616)}"
        )
    }
}
