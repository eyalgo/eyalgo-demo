package eyalgo.demo

import io.micronaut.runtime.Micronaut.run

object Application {
	@JvmStatic
	fun main(vararg args: String) {
		run(Application::class.java, *args)
	}
}
