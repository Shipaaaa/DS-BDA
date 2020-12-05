package ru.shipa.ignite.compute

import org.apache.ignite.Ignite
import org.apache.ignite.IgniteCache
import org.apache.ignite.compute.ComputeJob
import org.apache.ignite.compute.ComputeJobAdapter
import org.apache.ignite.compute.ComputeJobResult
import org.apache.ignite.compute.ComputeTaskSplitAdapter
import org.apache.ignite.resources.IgniteInstanceResource
import ru.shipa.core.entity.LogEntity
import ru.shipa.core.entity.LogPriority
import ru.shipa.ignite.compute.IgniteComputeApp.main
import ru.shipa.ignite.compute.entity.LogStat
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

/**
 * Ignite Compute application entry point.
 *
 * @author v.shipugin
 * @see main
 */
class CountLogsMapReduceTask : ComputeTaskSplitAdapter<String, List<LogStat>>() {

    @IgniteInstanceResource
    private lateinit var ignite: Ignite

    private lateinit var cache: IgniteCache<String, LogEntity>

    /**
     * Splitting log by hours
     *
     * @param gridSize size of Ignite Compute Grid
     * @param arg Ignite cache name
     *
     * @return split logs by hours
     */
    override fun split(gridSize: Int, arg: String): List<ComputeJob> {
        cache = ignite.getOrCreateCache(arg)

        return cache
            .iterator()
            .asSequence()
            .toList()
            .map { it.value }
            .map<LogEntity, ComputeJobAdapter> { log ->
                object : ComputeJobAdapter() {
                    override fun execute(): Pair<LocalDateTime, LogPriority> {
                        println(">>> Printing '$log' on from compute job.")

                        return log.dateTime.truncatedTo(ChronoUnit.HOURS) to log.priority
                    }
                }
            }
    }

    /**
     * Calculating log priority count by hours
     *
     * @param jobResults split logs by hours
     *
     * @return list of LogStat
     */
    override fun reduce(jobResults: List<ComputeJobResult>): List<LogStat> {
        val result = mutableMapOf<LocalDateTime, LogStat>()

        val groupedLogByHour: List<Pair<LocalDateTime, LogPriority>> = jobResults
            .map { it.getData() }

        groupedLogByHour
            .forEach { (hour, priority) ->
                val logStatistics = result
                    .getOrDefault(hour, LogStat(hour, mutableMapOf(priority to 0)))

                val count = logStatistics.statistics.getOrDefault(priority, 0)
                logStatistics.statistics[priority] = count + 1

                result[hour] = logStatistics
            }

        return result.values.toList()
    }
}