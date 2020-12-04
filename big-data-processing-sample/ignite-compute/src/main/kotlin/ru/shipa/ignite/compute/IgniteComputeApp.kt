package ru.shipa.ignite.compute

import org.apache.ignite.Ignition
import org.apache.ignite.cache.CacheAtomicityMode
import org.apache.ignite.cache.CacheMode
import org.apache.ignite.cluster.ClusterState
import org.apache.ignite.configuration.CacheConfiguration
import org.apache.ignite.configuration.IgniteConfiguration
import ru.shipa.core.entity.LogEntity
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
        }

        /**
         * Launching Apache Ignite
         */
        val ignite = Ignition.start(igniteCfg)

        val logs = ignite.getOrCreateCache<String, LogEntity>(DATA_CACHE_NAME)
            .asIterable()
            .toList()
            .map { it.value }

        val result = ignite.compute().execute(CountLogsMapReduceTask::class.java, DATA_CACHE_NAME)

        println("result: $result")
    }
}