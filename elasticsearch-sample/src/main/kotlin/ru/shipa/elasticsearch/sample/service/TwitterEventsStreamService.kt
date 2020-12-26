package ru.shipa.elasticsearch.sample.service

import org.springframework.social.twitter.api.StreamListener
import org.springframework.social.twitter.api.Twitter
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct

/**
 * Service for connecting to Twitter Stream API
 *
 * @author v.shipugin
 */
@Service
class TwitterEventsStreamService(
    private val twitter: Twitter,
    private val onTweetListener: StreamListener
) {

    /**
     * Start consuming from stream
     */
    @PostConstruct
    fun streamTweetEvent() {
        twitter.streamingOperations().sample(listOf(onTweetListener))
    }
}
