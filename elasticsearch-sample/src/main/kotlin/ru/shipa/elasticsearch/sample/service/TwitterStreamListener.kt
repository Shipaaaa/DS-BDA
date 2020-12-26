package ru.shipa.elasticsearch.sample.service

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.social.twitter.api.StreamDeleteEvent
import org.springframework.social.twitter.api.StreamListener
import org.springframework.social.twitter.api.StreamWarningEvent
import org.springframework.social.twitter.api.Tweet
import org.springframework.stereotype.Service

/**
 * StreamListener for connecting to Twitter Stream API
 *
 * @author v.shipugin
 */
@Service
class TwitterStreamListener(private val eventCounterService: EventCounterService) : StreamListener {

    private val log: Logger = LogManager.getLogger("TwitterStreamListener")

    /**
     * Processing of new tweet event
     * @param tweet tweet information
     */
    override fun onTweet(tweet: Tweet) {
        eventCounterService.incrementEventsCount()
        log.debug("tweets count incremented")
    }

    /**
     * Processing of delete event
     * @param deleteEvent streamDeleteEvent information
     */
    override fun onDelete(deleteEvent: StreamDeleteEvent) {
        if (log.isTraceEnabled) log.trace("onDelete called, but not implemented")
    }

    /**
     * Processing of reaching limit event
     * @param numberOfLimitedTweets limit
     */
    override fun onLimit(numberOfLimitedTweets: Int) {
        if (log.isWarnEnabled) log.warn("onLimit called, but not implemented")
    }

    /**
     * Processing of warning event
     * @param warningEvent streamWarningEvent information
     */
    override fun onWarning(warningEvent: StreamWarningEvent) {
        if (log.isWarnEnabled) log.warn("onWarning called, but not implemented")
    }
}
