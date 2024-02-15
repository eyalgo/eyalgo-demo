package eyalgo.demo.infrastructure.db.exposed

import eyalgo.demo.adapters.data.exposed.PersonRepositoryImpl
import io.micronaut.context.annotation.Context
import io.micronaut.context.annotation.Infrastructure
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction

@Context
@Infrastructure
class DatabaseSetup {
    init {
        println("----------------- DatabaseSetup -----------------")
        Database.connect("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", driver = "org.h2.Driver", user = "root", password = "")

        println("initialising PersonRepositoryImpl")

        /**
         * The creation of the database and the table should be using liquibase.
         * Persons can be private.
        */
        transaction {
            addLogger(StdOutSqlLogger)
            SchemaUtils.create(PersonRepositoryImpl.Persons)
        }
    }
}
