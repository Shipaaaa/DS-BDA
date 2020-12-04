package ru.shipa.kafka.producer

import org.apache.kafka.clients.producer.MockProducer
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringSerializer
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ru.shipa.core.entity.LogEntity
import ru.shipa.core.serializers.LogEntitySerializer

class KafkaLogsProducerTest {

    companion object {
        private const val TOPIC_NAME = "SYSLOG_TOPIC"
    }

    private val testLogs = listOf(
        "2020-05-10T19:19:09 VLAD-PC BARD_SIMPSON: <CRITICAL> I DID NOT WIN THE NOBEL FART PRIZE",
        "2020-12-16T17:39:52 VLAD-PC BARD_SIMPSON: <NOTICE> I AM NOT THE ACTING PRESIDENT",
        "2020-04-19T12:56:19 VLAD-PC BARD_SIMPSON: <INFORMATIONAL> I AM NOT THE ACTING PRESIDENT",
        "2020-12-09T16:15:57 VLAD-PC BARD_SIMPSON: <CRITICAL> I WILL NOT PLANT SUBLIMINAL MESSAGORES",
        "2020-02-05T12:34:26 VLAD-PC BARD_SIMPSON: <ALERT> I WILL NOT PLANT SUBLIMINAL MESSAGORES",
        "2020-11-22T04:27:22 VLAD-PC BARD_SIMPSON: <ERROR> I AM NOT THE ACTING PRESIDENT",
        "2020-04-09T02:52:34 VLAD-PC BARD_SIMPSON: <INFORMATIONAL> I AM NOT THE ACTING PRESIDENT",
        "2020-11-18T15:02:48 VLAD-PC BARD_SIMPSON: <INFORMATIONAL> I WILL NOT DANCE ON ANYONE’S GRAVE",
        "2020-05-17T05:37:57 VLAD-PC BARD_SIMPSON: <NOTICE> I WILL NOT DANCE ON ANYONE’S GRAVE",
        "2020-10-11T10:10:37 VLAD-PC BARD_SIMPSON: <DEBUG> I WILL NOT DANCE ON ANYONE’S GRAVE"
    )

    var mockProducer: MockProducer<String, LogEntity>? = null

    @BeforeEach
    fun setUp() {
        mockProducer = MockProducer(true, StringSerializer(), LogEntitySerializer())
    }

    @Test
    fun `when sending logs to kafka reducer - history should be correct`() {
        // given
        val testKafkaProducer = KafkaLogsProducer(mockProducer!!)

        // when
        testKafkaProducer.sendData(testLogs)

        // then
        val expectedRecords = testLogs.map { logs ->
            ProducerRecord(TOPIC_NAME, null, LogEntity.fromLine(logs))
        }

        val historyRecords = mockProducer!!.history()

        assertEquals(expectedRecords, historyRecords, "Sent didn't match expected")
    }
}