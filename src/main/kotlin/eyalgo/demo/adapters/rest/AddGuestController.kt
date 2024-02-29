package eyalgo.demo.adapters.rest

import eyalgo.demo.domain.model.Guest
import eyalgo.demo.ports.inbound.AddGuests
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpResponse.created
import io.micronaut.http.MediaType.APPLICATION_JSON
import io.micronaut.http.MediaType.TEXT_PLAIN
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post

@Controller("/guests")
class AddGuestController(private val addGuests: AddGuests) {

    @Post(consumes = [APPLICATION_JSON], produces = [TEXT_PLAIN])
    fun addGuest(@Body guest: GuestDTO): HttpResponse<Long> =
        created(addGuests.add(guest.toModel()))

    private fun GuestDTO.toModel(): Guest = Guest(firstName, lastName)
}
