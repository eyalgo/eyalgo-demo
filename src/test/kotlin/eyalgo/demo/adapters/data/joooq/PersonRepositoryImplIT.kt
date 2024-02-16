package eyalgo.demo.adapters.data.joooq

import eyalgo.demo.infrastructure.PostgresForTests
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import io.micronaut.test.extensions.testresources.annotation.TestResourcesProperties
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS

@MicronautTest(
    startApplication = false,
    environments = ["exposed"]
)
@TestInstance(PER_CLASS)
@TestResourcesProperties(providers = [PostgresForTests::class])
class PersonRepositoryImplIT {

    @Test
    @Disabled("Not yet implemented")
    fun `create and get a person`() {
        TODO("Not yet implemented")
    }
}
