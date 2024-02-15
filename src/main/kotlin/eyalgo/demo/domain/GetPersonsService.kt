package eyalgo.demo.domain

import eyalgo.demo.domain.model.Person
import eyalgo.demo.ports.PersonRepository
import io.micronaut.context.annotation.Bean
import jakarta.inject.Singleton

@Bean
@Singleton
class GetPersonsService(private val repository: PersonRepository) {
    fun getPersons(): List<Person> =
        listOf(
            Person("firstName-1", "lastName-1"),
            Person("firstName-2", "lastName-2")
        )

    fun getPerson(id: Long): Person = repository.getPerson(id)
}
