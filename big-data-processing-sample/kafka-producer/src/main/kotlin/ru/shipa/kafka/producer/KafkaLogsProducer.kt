package ru.shipa.kafka.producer

import org.apache.kafka.clients.producer.Producer
import org.apache.kafka.clients.producer.ProducerRecord
import org.slf4j.LoggerFactory
import ru.shipa.core.entity.LogEntity
import java.util.*

/**
 * Kafka producer. Sends data to Kafka Consumer.
 *
 * @author v.shipugin
 */
class KafkaLogsProducer(var producer: Producer<String, LogEntity>) {

    companion object {
        private const val TOPIC_NAME = "SYSLOG_TOPIC"
    }

    private val logger = LoggerFactory.getLogger(this::class.java)

    fun sendData(data: List<String>) {
        data
            .map { LogEntity.fromLine(it) }
            .map { ProducerRecord(TOPIC_NAME, UUID.randomUUID().toString(), it) }
            .forEach { record ->
                producer.send(record) { metadata, exception ->
                    metadata?.let { logger.debug("Message has been sent to topic: ${metadata.topic()}") }
                    exception?.let { logger.error(exception.message, exception) }
                }
            }
    }

    fun stop() {
        producer.close()
    }
}