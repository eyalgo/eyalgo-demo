package eyalgo.demo.adapters.data.exposed

import eyalgo.demo.containers.ContainerPostgres
import eyalgo.demo.teststrategies.ExposedIntegrationTest
import eyalgo.demo.domain.model.Guest
import eyalgo.demo.ports.GuestsRepository
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.shouldBe
import io.micronaut.test.extensions.testresources.annotation.TestResourcesProperties
import jakarta.inject.Inject
import org.junit.jupiter.api.Test

@ExposedIntegrationTest
@TestResourcesProperties(providers = [ContainerPostgres::class])
class GuestsRepositoryImplIT {
    @Inject
    lateinit var repo: GuestsRepository

    @Test
    fun `create and get a guest`() {
        val guest = Guest("Eyal", "Golan")
        val id = repo.createGuest(guest)

        repo.getGuest(id) shouldBe guest
    }

    @Test
    fun `get all guests`() {
        val guest1 = Guest("f1", "l1")
        val guest2 = Guest("f2", "l2")
        val guest3 = Guest("f3", "l3")

        repo.createGuest(guest1)
        repo.createGuest(guest2)
        repo.createGuest(guest3)

        repo.getGuests() shouldContainAll listOf(guest1, guest2, guest3)
    }
}
