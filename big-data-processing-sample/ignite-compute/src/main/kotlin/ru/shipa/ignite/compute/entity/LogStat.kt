package ru.shipa.ignite.compute.entity

import ru.shipa.core.entity.LogPriority
import java.time.LocalDateTime

data class LogStat(
    val hour: LocalDateTime,
    val statistics: MutableMap<LogPriority, Int>
)
