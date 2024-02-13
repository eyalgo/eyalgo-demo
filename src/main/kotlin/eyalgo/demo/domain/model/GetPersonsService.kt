package eyalgo.demo.domain.model

class GetPersonsService {
    fun getPersons(): List<Person> =
        listOf(
            Person("firstName-1", "lastName-1"),
            Person("firstName-2", "lastName-2")
        )
}
