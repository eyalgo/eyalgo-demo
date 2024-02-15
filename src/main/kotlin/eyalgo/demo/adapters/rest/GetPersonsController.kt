package eyalgo.demo.adapters.rest

import eyalgo.demo.domain.GetPersonsService
import eyalgo.demo.domain.model.Person
import io.micronaut.http.MediaType.APPLICATION_JSON
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get

@Controller("/persons")
class GetPersonsController(private val service: GetPersonsService) {
    @Get(produces = [APPLICATION_JSON])
    fun getPersons(): List<PersonDTO> = service.getPersons().toDTOs()

    @Get("/{id}", produces = [APPLICATION_JSON])
    fun getPerson(id: Long): PersonDTO = service.getPerson(id).toDTO()

    private fun Person.toDTO() = PersonDTO(firstName, lastName)
    private fun List<Person>.toDTOs() = map { it.toDTO() }
}
