package eyalgo.demo.usecases

import eyalgo.demo.adapters.rest.GuestDTO
import eyalgo.demo.containers.ContainerMySQL
import eyalgo.demo.teststrategies.UseCase
import io.micronaut.test.extensions.testresources.annotation.TestResourcesProperties
import org.junit.jupiter.api.Test

@UseCase
@TestResourcesProperties(providers = [ContainerMySQL::class])
class AddGuestUseCase : AbstractUseCase() {

    @Test
    fun `a guest is added to the DB`() = whenAddGuest(GuestDTO("Johnny", "Depp")).thenItShouldBePersisted()
}
