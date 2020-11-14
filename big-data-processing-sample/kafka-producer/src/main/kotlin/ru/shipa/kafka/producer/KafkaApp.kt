package ru.shipa.kafka.producer

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

        KafkaLogsProducer(readFile(sysLogsPath)).apply {
            init()
            stop()
        }
    }

    private fun readFile(sysLogsPath: String): String {
        println("Reading file...")

        return File(sysLogsPath)
            .readText(Charsets.UTF_8)
            .also { sysLogs -> println("File: ${sysLogs.take(50)} ... ${sysLogs.takeLast(50)}") }
    }
}