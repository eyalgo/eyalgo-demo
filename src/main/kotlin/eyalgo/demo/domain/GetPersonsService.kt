package eyalgo.demo.domain

import eyalgo.demo.domain.model.Person
import eyalgo.demo.ports.PersonRepository
import jakarta.inject.Singleton
import org.slf4j.LoggerFactory

@Singleton
class GetPersonsService(private val repository: PersonRepository) {
    private val log = LoggerFactory.getLogger(GetPersonsService::class.java)

    fun getPersons(): List<Person> {
        log.info("getting persons")
        return listOf(
            Person("firstName-1", "lastName-1"),
            Person("firstName-2", "lastName-2")
        )
    }

    fun getPerson(id: Long): Person = repository.getPerson(id)
}
