package eyalgo.demo

import io.micronaut.runtime.Micronaut.run
import io.micronaut.serde.annotation.SerdeImport
import org.flywaydb.core.internal.info.MigrationInfoImpl

object Application {
	@JvmStatic
	fun main(vararg args: String) {
		run(Application.javaClass, *args)
	}
}

@SerdeImport(MigrationInfoImpl::class)
object Serdes
