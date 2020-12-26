package ru.shipa.elasticsearch.sample.service

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.introspect.VisibilityChecker
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import ru.shipa.elasticsearch.sample.entity.SerializedMessageFactory


/**
 * Scheduler for sending tweets count to elasticsearch by log4j http appender
 *
 * @author v.shipugin
 */
@Service
@EnableScheduling
class EventCounterScheduler(private val eventCounterService: EventCounterService) {

    companion object {
        private const val TWEETS_COUNT = "tweets_count"
    }

    private val objectMapper: ObjectMapper by lazy {
        ObjectMapper()
            .setSerializationInclusion(JsonInclude.Include.NON_EMPTY)
            .setVisibility(
                VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY)
            )
    }

    private val logger: Logger = LogManager.getLogger("elasticsearch", SerializedMessageFactory(objectMapper))

    /**
     * Sending tweets count to elasticsearch by log4j http appender
     */
    @Scheduled(fixedDelayString = "\${log.count.period}")
    fun logTweetsCount() {
        val count = eventCounterService.getAndResetEventsCount()

        logger.info(count)
    }
}
