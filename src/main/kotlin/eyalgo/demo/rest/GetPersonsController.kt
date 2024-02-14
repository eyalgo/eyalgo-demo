package eyalgo.demo.rest

import eyalgo.demo.domain.model.GetPersonsService
import eyalgo.demo.domain.model.Person

class GetPersonsController(private val service: GetPersonsService) {
    fun getPersons(): List<Person> = service.getPersons()
}
