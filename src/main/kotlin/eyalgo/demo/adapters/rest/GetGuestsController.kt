package eyalgo.demo.adapters.rest

import eyalgo.demo.domain.GetGuestsService
import eyalgo.demo.domain.model.Guest
import io.micronaut.http.MediaType.APPLICATION_JSON
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable

@Controller("/guests")
class GetGuestsController(private val service: GetGuestsService) {
    @Get(produces = [APPLICATION_JSON])
    fun getGuests(): List<GuestDTO> = service.getGuests().toDTOs()

    @Get("/{id}", produces = [APPLICATION_JSON])
    fun getGuest(@PathVariable id: Long): GuestDTO = service.getGuest(id).toDTO()

    private fun Guest.toDTO() = GuestDTO(firstName, lastName)
    private fun List<Guest>.toDTOs() = map { it.toDTO() }
}
