package eyalgo.demo.adapters.data.joooq

import eyalgo.demo.ExposedIntegrationTest
import eyalgo.demo.containers.ContainerPostgres
import io.micronaut.test.extensions.testresources.annotation.TestResourcesProperties
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

@ExposedIntegrationTest
@TestResourcesProperties(providers = [ContainerPostgres::class])
class PersonRepositoryImplIT {

    @Test
    @Disabled("Not yet implemented")
    fun `create and get a person`() {
        TODO("Not yet implemented")
    }
}
