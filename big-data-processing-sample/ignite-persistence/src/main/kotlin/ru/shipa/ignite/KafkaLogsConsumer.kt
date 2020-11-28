package ru.shipa.ignite

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.serialization.StringDeserializer
import org.slf4j.LoggerFactory
import ru.shipa.core.entity.LogEntity
import ru.shipa.core.serializers.LogEntityDeserializer
import java.time.Duration

/**
 * Kafka consumer. Receives data from the Kafka producer.
 *
 * @author v.shipugin
 */
class KafkaLogsConsumer {

    companion object {
        private val BOOTSTRAP_SERVERS_IP = System.getenv("KAFKA_BOOTSTRAP_SERVERS_IP") ?: "127.0.0.1:9092"

        private const val TOPIC_NAME = "SYSLOG_TOPIC"

        private const val GROUP_ID = "SYSLOG_GROUP"
        private const val COMMIT_INTERVAL_MS = 1000
    }

    private val logger = LoggerFactory.getLogger(this::class.java)

    private val config = mapOf(
        ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to BOOTSTRAP_SERVERS_IP,
        ConsumerConfig.GROUP_ID_CONFIG to GROUP_ID,

        ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG to true,
        ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG to COMMIT_INTERVAL_MS,

        ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java.name,
        ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to LogEntityDeserializer::class.java.name
    )

    fun receiveMessage() {
        val consumer = KafkaConsumer<String, LogEntity>(config).apply {
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