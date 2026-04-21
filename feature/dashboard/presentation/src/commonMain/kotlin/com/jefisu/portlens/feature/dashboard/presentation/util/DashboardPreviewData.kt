package com.jefisu.portlens.feature.dashboard.presentation.util

import com.jefisu.portlens.core.domain.CompetenceMonth
import com.jefisu.portlens.core.domain.DashboardSnapshot
import com.jefisu.portlens.core.domain.ExemptionStatus
import com.jefisu.portlens.core.domain.LatestTransactionSummary
import com.jefisu.portlens.core.domain.Money
import com.jefisu.portlens.core.domain.Timestamp
import com.jefisu.portlens.core.domain.TransactionDate
import com.jefisu.portlens.core.domain.TransactionType
import kotlinx.datetime.Month

val dashboardSnapshotPreview = DashboardSnapshot(
    competence = CompetenceMonth(year = 2025, month = Month.NOVEMBER),
    lastUpdatedAt = Timestamp(year = 2025, month = 11, day = 18, hour = 14, minute = 32),
    monthlyExemptionLimit = Money(cents = 20_000_00),
    totalSoldInMonth = Money(cents = 12_340_00),
    remainingExemptionMargin = Money(cents = 7_660_00),
    realizedGainInMonth = Money(cents = 1_847_20),
    estimatedTaxInMonth = Money.Zero,
    exemptionStatus = ExemptionStatus.Exempt,
    usedLimitRatio = 0.617f,
    latestTransactions = listOf(
        LatestTransactionSummary(
            ticker = "PETR4",
            type = TransactionType.Buy,
            date = TransactionDate(year = 2025, month = 11, day = 3),
            quantity = 300,
            unitPrice = Money(cents = 28_50),
            operationVolume = Money(cents = 8_550_00),
            realizedResult = null,
            accumulatedMonthlyVolume = null,
        ),
        LatestTransactionSummary(
            ticker = "ITUB4",
            type = TransactionType.Sell,
            date = TransactionDate(year = 2025, month = 11, day = 12),
            quantity = 200,
            unitPrice = Money(cents = 32_10),
            operationVolume = Money(cents = 6_420_00),
            realizedResult = Money(cents = 842_00),
            accumulatedMonthlyVolume = Money(cents = 6_420_00),
        ),
        LatestTransactionSummary(
            ticker = "ITUB4",
            type = TransactionType.Rebuy,
            date = TransactionDate(year = 2025, month = 11, day = 13),
            quantity = 200,
            unitPrice = Money(cents = 32_15),
            operationVolume = Money(cents = 6_430_00),
            realizedResult = null,
            accumulatedMonthlyVolume = Money(cents = 6_420_00),
        ),
        LatestTransactionSummary(
            ticker = "VALE3",
            type = TransactionType.Sell,
            date = TransactionDate(year = 2025, month = 11, day = 18),
            quantity = 100,
            unitPrice = Money(cents = 59_20),
            operationVolume = Money(cents = 5_920_00),
            realizedResult = Money(cents = 1_005_20),
            accumulatedMonthlyVolume = Money(cents = 12_340_00),
        ),
    ),
)
