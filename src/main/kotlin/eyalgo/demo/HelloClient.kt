package eyalgo.demo

import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Get
import io.micronaut.http.client.annotation.Client
import io.micronaut.core.async.annotation.SingleResult
import org.reactivestreams.Publisher

@Client("/hello") // (1)
interface HelloClient {

    @Get(consumes = [MediaType.TEXT_PLAIN]) // (2)
    @SingleResult
    fun hello(): Publisher<String>  // (3)
}
