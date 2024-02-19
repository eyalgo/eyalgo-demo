package eyalgo.demo.matchers

import org.hamcrest.CoreMatchers
import org.hamcrest.Matcher

@Suppress("TestFunctionName")
fun <T>Is(value: T): Matcher<T> = CoreMatchers.`is`(value)
