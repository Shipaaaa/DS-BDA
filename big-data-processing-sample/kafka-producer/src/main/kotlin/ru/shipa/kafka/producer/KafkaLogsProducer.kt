package ru.shipa.kafka.producer

import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringSerializer
import org.slf4j.LoggerFactory
import ru.shipa.core.entity.LogEntity
import ru.shipa.core.serializers.LogEntitySerializer

/**
 * Kafka producer. Sends data to Kafka Consumer.
 *
 * @author v.shipugin
 */
class KafkaLogsProducer(private val data: List<String>) {

    companion object {
        private val BOOTSTRAP_SERVERS_IP = System.getenv("KAFKA_BOOTSTRAP_SERVERS_IP") ?: "127.0.0.1:9092"

        private const val TOPIC_NAME = "SYSLOG_TOPIC"
    }

    private val logger = LoggerFactory.getLogger(this::class.java)

    private val config = mapOf(
        ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to BOOTSTRAP_SERVERS_IP,

        ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java.name,
        ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to LogEntitySerializer::class.java.name,
    )

    private val producer = KafkaProducer<String, LogEntity>(config)

    fun sendData() {
        data
            .map { LogEntity.fromLine(it) }
            .map { ProducerRecord<String, LogEntity>(TOPIC_NAME, it) }
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