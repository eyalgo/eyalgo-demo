package eyalgo.demo

import io.restassured.response.ResponseBodyExtractionOptions
import org.hamcrest.CoreMatchers
import org.hamcrest.Matcher

@Suppress("TestFunctionName")
fun <T> Is(value: T): Matcher<T> = CoreMatchers.`is`(value)

@Suppress("TestFunctionName")
infix fun <T> ResponseBodyExtractionOptions.As(clazz: Class<T>): T = this.`as`(clazz)
