package com.jefisu.portlens

import com.jefisu.portlens.core.domain.CompetenceMonth
import com.jefisu.portlens.core.domain.DashboardSnapshot
import com.jefisu.portlens.core.domain.ExemptionStatus
import com.jefisu.portlens.core.domain.GetAvailableCompetences
import com.jefisu.portlens.core.domain.GetDashboardSnapshot
import com.jefisu.portlens.core.domain.LatestTransactionSummary
import com.jefisu.portlens.core.domain.Money
import com.jefisu.portlens.core.domain.Timestamp
import com.jefisu.portlens.core.domain.TransactionDate
import com.jefisu.portlens.core.domain.TransactionType
import com.jefisu.portlens.designsystem.components.shell.CompetenceIndicatorToneUi
import com.jefisu.portlens.feature.dashboard.presentation.util.dashboardSnapshotPreview
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun fakeGetAvailableCompetences(): GetAvailableCompetences =
    GetAvailableCompetences { currentLocalCompetence ->
        fakeAvailableCompetences(currentLocalCompetence)
    }

fun fakeGetDashboardSnapshot(): GetDashboardSnapshot = GetDashboardSnapshot { competence ->
    fakeDashboardSnapshotFor(competence)
}

fun fakeDashboardSnapshotFor(competence: CompetenceMonth): DashboardSnapshot =
    fakeSnapshotsByCompetence[competence] ?: emptyFakeDashboardSnapshot(competence)

fun fakeCompetenceIndicatorToneFor(competence: CompetenceMonth): CompetenceIndicatorToneUi? {
    val snapshot = fakeSnapshotsByCompetence[competence] ?: return null
    val hasPositiveSell = snapshot.latestTransactions.any { transaction ->
        transaction.type == TransactionType.Sell && (transaction.realizedResult?.cents ?: 0L) > 0
    }

    return when {
        snapshot.estimatedTaxInMonth.cents > 0 -> CompetenceIndicatorToneUi.Negative
        hasPositiveSell -> CompetenceIndicatorToneUi.Positive
        snapshot.latestTransactions.isNotEmpty() -> CompetenceIndicatorToneUi.Warning
        else -> null
    }
}

private val fakeSnapshots: List<DashboardSnapshot> = listOf(
    DashboardSnapshot(
        competence = CompetenceMonth(year = 2025, month = Month.SEPTEMBER),
        lastUpdatedAt = Timestamp(year = 2025, month = 9, day = 23, hour = 18, minute = 5),
        monthlyExemptionLimit = Money(cents = 20_000_00),
        totalSoldInMonth = Money(cents = 9_800_00),
        remainingExemptionMargin = Money(cents = 10_200_00),
        realizedGainInMonth = Money.Zero,
        estimatedTaxInMonth = Money.Zero,
        exemptionStatus = ExemptionStatus.Exempt,
        usedLimitRatio = 0.49f,
        latestTransactions = listOf(
            LatestTransactionSummary(
                ticker = "ABEV3",
                type = TransactionType.Buy,
                date = TransactionDate(year = 2025, month = 9, day = 8),
                quantity = 200,
                unitPrice = Money(cents = 13_45),
                operationVolume = Money(cents = 2_690_00),
                realizedResult = null,
                accumulatedMonthlyVolume = null,
            ),
            LatestTransactionSummary(
                ticker = "ABEV3",
                type = TransactionType.Sell,
                date = TransactionDate(year = 2025, month = 9, day = 23),
                quantity = 200,
                unitPrice = Money(cents = 13_45),
                operationVolume = Money(cents = 2_690_00),
                realizedResult = Money.Zero,
                accumulatedMonthlyVolume = Money(cents = 9_800_00),
            ),
        ),
    ),
    DashboardSnapshot(
        competence = CompetenceMonth(year = 2025, month = Month.OCTOBER),
        lastUpdatedAt = Timestamp(year = 2025, month = 10, day = 29, hour = 16, minute = 12),
        monthlyExemptionLimit = Money(cents = 20_000_00),
        totalSoldInMonth = Money(cents = 28_500_00),
        remainingExemptionMargin = Money.Zero,
        realizedGainInMonth = Money(cents = 3_420_00),
        estimatedTaxInMonth = Money(cents = 513_00),
        exemptionStatus = ExemptionStatus.Taxable,
        usedLimitRatio = 1f,
        latestTransactions = listOf(
            LatestTransactionSummary(
                ticker = "PRIO3",
                type = TransactionType.Sell,
                date = TransactionDate(year = 2025, month = 10, day = 29),
                quantity = 300,
                unitPrice = Money(cents = 95_00),
                operationVolume = Money(cents = 28_500_00),
                realizedResult = Money(cents = 3_420_00),
                accumulatedMonthlyVolume = Money(cents = 28_500_00),
            ),
        ),
    ),
    dashboardSnapshotPreview,
)

private val fakeSnapshotsByCompetence: Map<CompetenceMonth, DashboardSnapshot> =
    fakeSnapshots.associateBy { it.competence }

private fun fakeAvailableCompetences(
    currentLocalCompetence: CompetenceMonth,
): List<CompetenceMonth> {
    val oldestCompetence = fakeSnapshots
        .minByOrNull { it.competence.absoluteMonthIndex() }
        ?.competence
        ?: currentLocalCompetence
    return if (oldestCompetence <= currentLocalCompetence) {
        competenceRange(start = oldestCompetence, end = currentLocalCompetence)
    } else {
        listOf(currentLocalCompetence)
    }
}

private fun emptyFakeDashboardSnapshot(competence: CompetenceMonth): DashboardSnapshot =
    DashboardSnapshot(
        competence = competence,
        lastUpdatedAt = currentTimestamp(),
        monthlyExemptionLimit = Money(cents = 20_000_00),
        totalSoldInMonth = Money.Zero,
        remainingExemptionMargin = Money(cents = 20_000_00),
        realizedGainInMonth = Money.Zero,
        estimatedTaxInMonth = Money.Zero,
        exemptionStatus = ExemptionStatus.Exempt,
        usedLimitRatio = 0f,
        latestTransactions = emptyList(),
    )

private fun competenceRange(start: CompetenceMonth, end: CompetenceMonth): List<CompetenceMonth> {
    val startIndex = start.absoluteMonthIndex()
    val endIndex = end.absoluteMonthIndex()

    return (startIndex..endIndex).map { absoluteMonthIndex ->
        val year = absoluteMonthIndex / 12
        val month = Month.entries[absoluteMonthIndex % 12]
        CompetenceMonth(year = year, month = month)
    }
}

private fun CompetenceMonth.absoluteMonthIndex(): Int = year * 12 + month.ordinal

private operator fun CompetenceMonth.compareTo(other: CompetenceMonth): Int =
    absoluteMonthIndex().compareTo(other.absoluteMonthIndex())

private fun currentTimestamp(): Timestamp {
    val now = kotlin.time.Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    return Timestamp(
        year = now.year,
        month = now.month.ordinal + 1,
        day = now.day,
        hour = now.hour,
        minute = now.minute,
    )
}
