package eyalgo.demo.adapters.data.exposed

import eyalgo.demo.domain.model.Person
import eyalgo.demo.ports.PersonRepository
import io.micronaut.context.annotation.Requires
import jakarta.inject.Singleton
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

@Singleton
@Requires(env = ["exposed"])
class PersonRepositoryImpl: PersonRepository {

    object Persons : LongIdTable("person") {
        val firstName: Column<String> = varchar("first_name", length = 100)
        val lastName: Column<String> = varchar("last_name", length = 100)
    }

    override fun createPerson(person: Person): Long = transaction {
            addLogger(StdOutSqlLogger)
            Persons.insertAndGetId {
                it[firstName] = person.firstName
                it[lastName] = person.lastName
            }.value
        }

    override fun getPerson(id: Long): Person = transaction {
        addLogger(StdOutSqlLogger)
        Persons.selectAll()
            .where { Persons.id eq id }
            .map { Person(it[Persons.firstName], it[Persons.lastName]) }
            .single()
    }

    override fun getPersons(): List<Person> = transaction {
        Persons.selectAll()
            .map { Person(it[Persons.firstName], it[Persons.lastName]) }
    }
}
