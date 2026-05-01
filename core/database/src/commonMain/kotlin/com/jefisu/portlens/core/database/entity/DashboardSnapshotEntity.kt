package com.jefisu.portlens.core.database.entity

import androidx.room3.Entity

@Entity(
    tableName = "dashboard_snapshots",
    primaryKeys = ["competenceYear", "competenceMonth"],
)
data class DashboardSnapshotEntity(
    val competenceYear: Int,
    val competenceMonth: Int,
    val lastUpdatedYear: Int,
    val lastUpdatedMonth: Int,
    val lastUpdatedDay: Int,
    val lastUpdatedHour: Int,
    val lastUpdatedMinute: Int,
    val monthlyExemptionLimitCents: Long,
    val totalSoldInMonthCents: Long,
    val remainingExemptionMarginCents: Long,
    val realizedGainInMonthCents: Long,
    val estimatedTaxInMonthCents: Long,
    val exemptionStatus: String,
    val usedLimitRatio: Float,
)
