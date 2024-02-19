package eyalgo.demo

import io.micrometer.core.instrument.MeterRegistry
import io.micronaut.http.MediaType.TEXT_PLAIN
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get

@Controller("/hello")
class HelloController(private val meterRegistry: MeterRegistry) {

    @Get("1", produces = [TEXT_PLAIN])
    fun helloWorld(): String {
        meterRegistry
            .counter("hello.world.1.counter")
            .increment()
        return "Hello World 1"
    }
}
