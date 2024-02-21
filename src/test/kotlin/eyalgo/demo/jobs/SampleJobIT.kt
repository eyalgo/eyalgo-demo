package eyalgo.demo.jobs

import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldNotContain
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import java.util.concurrent.TimeUnit.SECONDS
import nl.altindag.log.LogCaptor
import org.awaitility.kotlin.atMost
import org.awaitility.kotlin.await
import org.awaitility.kotlin.untilAsserted
import org.awaitility.kotlin.withPollInterval
import org.awaitility.pollinterval.FixedPollInterval
import org.junit.jupiter.api.Test

@MicronautTest
class SampleJobIT {
    private val logCaptor: LogCaptor = LogCaptor.forClass(SampleJob::class.java)

    @Test
    fun `check job`() {
        val seconds5: java.time.Duration = java.time.Duration.ofSeconds(5)
        val pollInterval = FixedPollInterval(3, SECONDS)
        await withPollInterval pollInterval atMost seconds5 untilAsserted {
            logCaptor.infoLogs shouldNotContain "Running sample job"
        }
    }

    @Test
    fun `check job schedule`() {
        val seconds20: java.time.Duration = java.time.Duration.ofSeconds(20)
        val pollInterval = FixedPollInterval(3, SECONDS)
        await withPollInterval pollInterval atMost seconds20 untilAsserted {
            logCaptor.infoLogs shouldContain  "Running sample job"
        }
    }
}
