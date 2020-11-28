package ru.shipa.kafka.producer

import ru.shipa.kafka.producer.KafkaApp.main
import java.io.File


/**
 * Kafka application entry point.
 *
 * @author v.shipugin
 * @see main
 */
object KafkaApp {

    @JvmStatic
    fun main(args: Array<String>) {
        val sysLogsPath = args[0]
        val data = readFile(sysLogsPath)

        KafkaLogsProducer(data).apply {
            sendData()
            stop()
        }
    }

    private fun readFile(sysLogsPath: String): List<String> {
        println("Reading file...")

        return File(sysLogsPath)
            .useLines { it.toList() }
            .also { sysLogs -> println("File:\n${sysLogs.firstOrNull()}\n...\n${sysLogs.lastOrNull()}\n") }
    }
}