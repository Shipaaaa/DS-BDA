package ru.shipa.elasticsearch.sample.service

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

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

    private val logger: Logger = LogManager.getLogger("elasticsearch")

    /**
     * Sending tweets count to elasticsearch by log4j http appender
     */
    @Scheduled(fixedDelayString = "\${log.count.period}")
    fun logTweetsCount() {
        val count = eventCounterService.getAndResetTweetsCount()

        logger.info("Event Name: $TWEETS_COUNT, count : $count")
    }
}
