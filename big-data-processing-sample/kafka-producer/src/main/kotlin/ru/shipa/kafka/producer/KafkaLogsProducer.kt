package ru.shipa.kafka.producer

import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.ByteArraySerializer
import org.apache.kafka.common.serialization.StringSerializer
import org.slf4j.LoggerFactory

/**
 * Kafka producer. Sends data to Kafka Consumer.
 *
 * @author v.shipugin
 */
class KafkaLogsProducer(private val data: String) {

    companion object {
        private val BOOTSTRAP_SERVERS_IP = System.getenv("KAFKA_BOOTSTRAP_SERVERS_IP") ?: "127.0.0.1:9092"

        private const val TOPIC_NAME = "SYSLOG_TOPIC"
    }

    private val logger = LoggerFactory.getLogger(this::class.java)

    private val config = mapOf(
        ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to BOOTSTRAP_SERVERS_IP,

        ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java.name,
        ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to ByteArraySerializer::class.java.name,
    )

    private val producer = KafkaProducer<String, ByteArray>(config)

    fun init() {
        val message = data.toByteArray()

        val record = ProducerRecord<String, ByteArray>(TOPIC_NAME, message)

        producer.send(record) { metadata, exception ->
            metadata?.let { logger.debug("Сообщение отправлено в topic: ${metadata.topic()}") }
            exception?.let { logger.error(exception.message, exception) }
        }
    }

    fun stop() {
        producer.close()
    }
}