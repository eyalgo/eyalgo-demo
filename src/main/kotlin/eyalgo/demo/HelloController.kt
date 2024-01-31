package eyalgo.demo

import io.micronaut.http.MediaType.TEXT_PLAIN
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get

@Controller("/hello") // (1)
class HelloController {

    @Get(produces = [TEXT_PLAIN]) // (2)
    fun helloWorld(): String {
        return "Hello World" // (3)
    }
}
