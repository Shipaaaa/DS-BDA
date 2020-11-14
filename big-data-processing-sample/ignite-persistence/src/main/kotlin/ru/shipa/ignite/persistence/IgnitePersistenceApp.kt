package ru.shipa.ignite.persistence

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