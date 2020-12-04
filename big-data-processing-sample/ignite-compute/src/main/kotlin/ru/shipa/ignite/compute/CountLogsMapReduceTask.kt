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
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

class CountLogsMapReduceTask : ComputeTaskSplitAdapter<String, List<Pair<LocalDateTime, Pair<LogPriority, Int>>>>() {

    @IgniteInstanceResource
    private lateinit var ignite: Ignite

    private lateinit var cache: IgniteCache<String, LogEntity>

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

    override fun reduce(results: List<ComputeJobResult>): List<Pair<LocalDateTime, Pair<LogPriority, Int>>> {
        val logs = mutableMapOf<LocalDateTime, Pair<LogPriority, Int>>()

        val grouped: Map<LocalDateTime, List<ComputeJobResult>> = results
            .groupBy { result -> result.getData<Pair<LocalDateTime, LogPriority>>().first }


        for (key in grouped.keys) {
            grouped
                .getValue(key)
                .map { it.getData<Pair<LocalDateTime, LogPriority>>() }
                .forEach { (_, priority) ->
                    val second: Int = (logs[key]?.second ?: 0) + 1
                    logs[key] = priority to second
                }

        }

        return logs.toList()
    }
}