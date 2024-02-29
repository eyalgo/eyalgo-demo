package eyalgo.demo.adapters.data.exposed

import eyalgo.demo.containers.ContainerMySQL
import eyalgo.demo.teststrategies.ExposedIntegrationTest
import eyalgo.demo.domain.model.Guest
import eyalgo.demo.ports.GuestsRepository
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.shouldBe
import io.micronaut.test.extensions.testresources.annotation.TestResourcesProperties
import jakarta.inject.Inject
import java.util.UUID.randomUUID
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.Test

@ExposedIntegrationTest
@TestResourcesProperties(providers = [ContainerMySQL::class])
class GuestsRepositoryImplIT {
    @Inject
    lateinit var repo: GuestsRepository

    @Test
    fun `create and get a guest`() {
        val guest = Guest("Eyal", "Golan")
        val messageId = randomUUID()
        val id = repo.createGuest(messageId, guest)

        repo.getGuest(id) shouldBe guest

        var guestFromDb: Guest? = null
        transaction {
            val mappedGuestId = MessageIdToGuestId.selectAll()
                .where { MessageIdToGuestId.messageId eq messageId }
                .map { it[MessageIdToGuestId.guestId] }
                .single()

            guestFromDb = Guests.selectAll()
                .where { Guests.id eq mappedGuestId }
                .map { Guest(it[Guests.firstName], it[Guests.lastName]) }
                .single()
        }
        guestFromDb shouldBe guest
    }

    @Test
    fun `get all guests`() {
        val guest1 = Guest("f1", "l1")
        val guest2 = Guest("f2", "l2")
        val guest3 = Guest("f3", "l3")

        repo.createGuest(randomUUID(), guest1)
        repo.createGuest(randomUUID(), guest2)
        repo.createGuest(randomUUID(), guest3)

        repo.getGuests() shouldContainAll listOf(guest1, guest2, guest3)
    }
}
