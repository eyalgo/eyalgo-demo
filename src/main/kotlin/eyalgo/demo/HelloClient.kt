package eyalgo.demo

import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Get
import io.micronaut.http.client.annotation.Client
import io.micronaut.core.async.annotation.SingleResult
import io.micronaut.http.MediaType.TEXT_PLAIN
import org.reactivestreams.Publisher

@Client("/hello")
interface HelloClient {

    @Get(consumes = [TEXT_PLAIN])
    @SingleResult
    fun hello(): Publisher<String>
}
