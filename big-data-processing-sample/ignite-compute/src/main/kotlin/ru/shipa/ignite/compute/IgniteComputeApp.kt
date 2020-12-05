package ru.shipa.ignite.compute

import org.apache.ignite.Ignition
import org.apache.ignite.configuration.IgniteConfiguration
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi
import org.apache.ignite.spi.discovery.tcp.ipfinder.multicast.TcpDiscoveryMulticastIpFinder
import ru.shipa.ignite.compute.IgniteComputeApp.main

/**
 * Ignite Compute application entry point.
 *
 * @author v.shipugin
 * @see main
 */
object IgniteComputeApp {

    private const val DATA_CACHE_NAME = "data_cache_name"

    @JvmStatic
    fun main(args: Array<String>) {
        /**
         * Apache Ignite configuration
         */
        val igniteCfg = IgniteConfiguration().apply {
            isPeerClassLoadingEnabled = true
            isClientMode = true

            val ipFinder = TcpDiscoveryMulticastIpFinder().apply {
                setAddresses(listOf("127.0.0.1:47500..47509"))
            }
            discoverySpi = TcpDiscoverySpi().setIpFinder(ipFinder)
        }

        /**
         * Launching Apache Ignite
         */
        val ignite = Ignition.start(igniteCfg)

        val result = ignite.compute().execute(CountLogsMapReduceTask::class.java, DATA_CACHE_NAME)

        println("result:")
        result.forEach { println(it) }

        ignite.close()
    }
}