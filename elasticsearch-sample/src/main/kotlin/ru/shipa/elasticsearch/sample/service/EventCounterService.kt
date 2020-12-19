package ru.shipa.elasticsearch.sample.service

import org.springframework.stereotype.Service
import java.util.concurrent.atomic.AtomicLong

/**
 * Service for counting tweets
 *
 * @author v.shipugin
 */
@Service
class EventCounterService {

    private val tweetsCount = AtomicLong(0)

    /**
     * Increment tweets count
     */
    fun incrementTweetsCount() {
        tweetsCount.incrementAndGet()
    }

    /**
     * Return and reset tweets count
     * @return tweets count
     */
    fun getAndResetTweetsCount() = tweetsCount.get().also { tweetsCount.set(0) }
}
