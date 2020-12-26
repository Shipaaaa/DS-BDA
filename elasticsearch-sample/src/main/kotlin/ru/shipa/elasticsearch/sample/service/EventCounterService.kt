package ru.shipa.elasticsearch.sample.service

import org.springframework.stereotype.Service
import java.util.concurrent.atomic.AtomicLong

/**
 * Service for counting events
 *
 * @author v.shipugin
 */
@Service
class EventCounterService {

    private val eventCount = AtomicLong(0)

    /**
     * Increment events count
     */
    fun incrementEventsCount() {
        eventCount.incrementAndGet()
    }

    /**
     * Return and reset events count
     *
     * @return events count
     */
    fun getAndResetEventsCount() = eventCount.get().also { eventCount.set(0) }
}
