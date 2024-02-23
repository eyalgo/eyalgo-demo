package eyalgo.demo.teststrategies

import eyalgo.demo.adapters.data.exposed.PersonRepositoryImpl.Persons
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext

class ExposedTestExtension : BeforeEachCallback, AfterEachCallback {
    override fun beforeEach(extensionContext: ExtensionContext) {
        transaction { Persons.deleteAll() }
    }

    override fun afterEach(context: ExtensionContext) {
        transaction { Persons.deleteAll() }
    }
}
