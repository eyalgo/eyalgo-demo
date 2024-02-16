package eyalgo.demo.adapters.data.joooq

import eyalgo.demo.domain.model.Person
import eyalgo.demo.ports.PersonRepository
import io.micronaut.context.annotation.Requires
import jakarta.inject.Singleton

@Singleton
@Requires(env = ["jooq"])
class PersonRepositoryImpl: PersonRepository {
    override fun createPerson(person: Person): Long {
        TODO("Not yet implemented")
    }

    override fun getPerson(id: Long): Person {
        TODO("Not yet implemented")
    }
}
