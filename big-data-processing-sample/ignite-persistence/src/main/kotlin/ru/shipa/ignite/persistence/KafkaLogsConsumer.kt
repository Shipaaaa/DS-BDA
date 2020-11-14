package ru.shipa.ignite.persistence

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.serialization.StringDeserializer
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.time.Duration

/**
 * Kafka consumer. Receives data from the Kafka producer.
 *
 * @author v.shipugin
 */
class KafkaLogsConsumer {

    companion object {
        private const val TOPIC_NAME = "SYSLOG_TOPIC"
        private const val BOOTSTRAP_SERVERS_IP = "0.0.0.0:32778"

        private const val GROUP_ID = "SYSLOG_GROUP"
        private const val COMMIT_INTERVAL_MS = 1000
    }

    private val logger = LoggerFactory.getLogger(this::class.java)

    private val config = mapOf(
        ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to BOOTSTRAP_SERVERS_IP,
        ConsumerConfig.GROUP_ID_CONFIG to GROUP_ID,

        ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG to true,
        ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG to COMMIT_INTERVAL_MS,

        ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java.name,
        ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java.name
    )

    fun receiveMessage() {
        val consumer = KafkaConsumer<String, String>(config).apply {
            subscribe(listOf(TOPIC_NAME))
        }

        while (true) {
            val records = consumer.poll(Duration.ofMillis(COMMIT_INTERVAL_MS.toLong()))

            for (record in records) {
                // TODO Сохранять полученные данные в Ignite Native Persistence
                logger.debug("record value: ${record.value()}")
            }
        }
    }
}