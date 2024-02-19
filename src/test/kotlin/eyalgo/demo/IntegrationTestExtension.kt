package eyalgo.demo

import io.micronaut.context.ApplicationContext
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.runtime.server.EmbeddedServer
import io.micronaut.test.annotation.MicronautTestValue
import io.micronaut.test.context.TestContext
import io.micronaut.test.extensions.AbstractMicronautExtension
import io.micronaut.test.extensions.junit5.MicronautJunit5Extension
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.api.extension.ParameterContext
import org.junit.jupiter.api.extension.ParameterResolver

class IntegrationTestExtension: BeforeEachCallback {
    companion object {
        @JvmStatic
        private val namespace: ExtensionContext.Namespace = ExtensionContext.Namespace.create(IntegrationTestExtension::class.java)
    }

//    @field:Client("/")
//    lateinit var client: HttpClient

//    override fun supportsParameter(parameterContext: ParameterContext, extensionContext: ExtensionContext): Boolean =
//        parameterContext.parameter.type == HttpClient::class.java
//
//    override fun resolveParameter(parameterContext: ParameterContext, extensionContext: ExtensionContext): Any =
//        client

    override fun beforeEach(extensionContext: ExtensionContext) {
//        super.beforeEach(extensionContext)
//        val server = applicationContext.getBean(EmbeddedServer::class.java)
//        server.start()
//        client = server.applicationContext.createBean(HttpClient::class.java, server.url)
    }

//    override fun beforeAll(extensionContext: ExtensionContext) {
//        val testClass = extensionContext.requiredTestClass
//        val integrationTestValue: MicronautTestValue = buildIntegrationTestValue(testClass)
//        beforeClass(extensionContext, extensionContext.requiredTestClass, integrationTestValue)
//        extensionContext.root.getStore(namespace).put(ApplicationContext::class.java, applicationContext)
//        beforeTestClass(buildContext(extensionContext))
//    }

//    private fun buildIntegrationTestValue(testClass: Class<*>): MicronautTestValue =
//        AnnotationSupport
//            .findAnnotation(testClass, MicronautTest::class.java)
//            .map(this::buildValueObject)
//            .orElse(null)

//    private fun buildValueObject(micronautTest: MicronautTest): MicronautTestValue =
//        MicronautTestValue(
//            micronautTest.application(),
//            micronautTest.environments(),
//            micronautTest.packages(),
//            micronautTest.propertySources(),
//            micronautTest.rollback(),
//            micronautTest.transactional(),
//            micronautTest.rebuildContext(),
//            micronautTest.contextBuilder(),
//            micronautTest.transactionMode(),
//            micronautTest.startApplication(),
//            micronautTest.resolveParameters())
////        IntegrationTestValue("production")

}
