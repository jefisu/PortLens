package com.jefisu.portlens.core.domain

import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun emptyDashboardSnapshotFor(
    competence: CompetenceMonth,
    lastUpdatedAt: Timestamp = currentTimestamp(),
): DashboardSnapshot = DashboardSnapshot(
    competence = competence,
    lastUpdatedAt = lastUpdatedAt,
    monthlyExemptionLimit = Money(cents = 20_000_00),
    totalSoldInMonth = Money.Zero,
    remainingExemptionMargin = Money(cents = 20_000_00),
    realizedGainInMonth = Money.Zero,
    estimatedTaxInMonth = Money.Zero,
    exemptionStatus = ExemptionStatus.Exempt,
    usedLimitRatio = 0f,
    latestTransactions = emptyList(),
)

fun competenceRange(start: CompetenceMonth, end: CompetenceMonth): List<CompetenceMonth> {
    val startIndex = start.absoluteMonthIndex()
    val endIndex = end.absoluteMonthIndex()

    return (startIndex..endIndex).map { absoluteMonthIndex ->
        val year = absoluteMonthIndex / 12
        val month = Month.entries[absoluteMonthIndex % 12]
        CompetenceMonth(year = year, month = month)
    }
}

operator fun CompetenceMonth.compareTo(other: CompetenceMonth): Int =
    absoluteMonthIndex().compareTo(other.absoluteMonthIndex())

fun currentTimestamp(): Timestamp {
    val now = kotlin.time.Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    return Timestamp(
        year = now.year,
        month = now.month.ordinal + 1,
        day = now.day,
        hour = now.hour,
        minute = now.minute,
    )
}

private fun CompetenceMonth.absoluteMonthIndex(): Int = year * 12 + month.ordinal
