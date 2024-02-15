package eyalgo.demo.adapters.data.exposed

import eyalgo.demo.domain.model.Person
import eyalgo.demo.ports.PersonRepository
import io.micronaut.context.annotation.Bean
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

@Bean
class PersonRepositoryImpl: PersonRepository {
    init {
        /**
         * The creation of the database and the table should be using liquibase.
         */
        println("initialising PersonRepositoryImpl")

        Database.connect("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", driver = "org.h2.Driver", user = "root", password = "")
        transaction {
            addLogger(StdOutSqlLogger)
            SchemaUtils.create(Persons)
        }
    }

    private object Persons : Table("person") {
        val id: Column<Long> = long("id").autoIncrement()
        val firstName: Column<String> = varchar("first_name", length = 100)
        val lastName: Column<String> = varchar("last_name", length = 100)
    }

    override fun createPerson(person: Person): Long {
        return transaction {
            addLogger(StdOutSqlLogger)
            Persons.insert {
                it[firstName] = person.firstName
                it[lastName] = person.lastName
            } get Persons.id
        }
    }

    override fun getPerson(id: Long): Person = transaction {
        addLogger(StdOutSqlLogger)
        Persons.selectAll()
            .where { Persons.id eq id }
            .map { Person(it[Persons.firstName], it[Persons.lastName]) }
            .single()
    }
}
