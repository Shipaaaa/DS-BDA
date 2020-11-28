package ru.shipa.ignite

/**
 * Ignite Persistence application entry point.
 *
 * @author v.shipugin
 * @see main
 */
object IgnitePersistenceApp {

    @JvmStatic
    fun main(args: Array<String>) {
        KafkaLogsConsumer().receiveMessage()
    }
}