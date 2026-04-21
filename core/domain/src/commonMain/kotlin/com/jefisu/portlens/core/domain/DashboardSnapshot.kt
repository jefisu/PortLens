package com.jefisu.portlens.core.domain

data class DashboardSnapshot(
    val competence: CompetenceMonth,
    val lastUpdatedAt: Timestamp,
    val monthlyExemptionLimit: Money,
    val totalSoldInMonth: Money,
    val remainingExemptionMargin: Money,
    val realizedGainInMonth: Money,
    val estimatedTaxInMonth: Money,
    val exemptionStatus: ExemptionStatus,
    val usedLimitRatio: Float,
    val latestTransactions: List<LatestTransactionSummary>,
)
