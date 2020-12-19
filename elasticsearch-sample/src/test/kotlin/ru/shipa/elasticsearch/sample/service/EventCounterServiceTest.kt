package ru.shipa.elasticsearch.sample.service

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.internal.util.Supplier
import org.springframework.boot.test.context.SpringBootTest
import java.lang.Long.sum
import java.util.stream.LongStream
import java.util.stream.Stream
import kotlin.test.junit5.JUnit5Asserter.assertEquals

@SpringBootTest
internal class EventCounterServiceTest {

    private lateinit var eventCounterService: EventCounterService

    @BeforeEach
    fun setUp() {
        eventCounterService = EventCounterService()
    }

    @Test
    fun `when events happened - event counter value should be correct`() {

        val inputStreamSupplier = Supplier { Stream.of(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L) }

        val expectedResult = inputStreamSupplier.get().reduce(0) { a, b -> sum(a, b) }

        inputStreamSupplier.get().parallel().forEach { count ->
            LongStream.rangeClosed(1L, count).forEach { eventCounterService.incrementEventsCount() }
        }

        val result: Long = eventCounterService.getAndResetEventsCount()

        assertEquals("message", expectedResult, result)
    }
}