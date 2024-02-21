package eyalgo.demo.jobs

import io.micronaut.scheduling.annotation.Scheduled
import jakarta.inject.Singleton
import org.slf4j.LoggerFactory

@Singleton
class SampleJob {
    companion object {
        private val log = LoggerFactory.getLogger(SampleJob::class.java)
    }

    @Scheduled(fixedDelay = "9s", initialDelay = "5s")
    fun run() {
        log.info("Running sample job")
    }
}
