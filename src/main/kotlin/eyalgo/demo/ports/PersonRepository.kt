package eyalgo.demo.ports

import eyalgo.demo.domain.model.Person

interface PersonRepository {
    fun createPerson(person: Person): Long
    fun getPerson(id: Long): Person
}
