package ru.shipa.elasticsearch.sample

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.logging.LoggingSystem
import org.springframework.boot.runApplication
import org.springframework.context.ConfigurableApplicationContext
import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.LockSupport

@SpringBootApplication
class Application

fun main(args: Array<String>) {
    val applicationContext: ConfigurableApplicationContext = runApplication<Application>(*args)

    val logger: Logger = LogManager.getLogger("elasticsearch")
    logger.info("Hello, World!")

    LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(1))

    applicationContext.close()
    LoggingSystem.get(logger::class.java.classLoader)
        .shutdownHandler
        .run()
}
