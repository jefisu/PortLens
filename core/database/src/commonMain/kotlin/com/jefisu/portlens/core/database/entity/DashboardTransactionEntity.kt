package com.jefisu.portlens.core.database.entity

import androidx.room3.Entity

@Entity(
    tableName = "latest_transactions",
    primaryKeys = ["competenceYear", "competenceMonth", "orderIndex"],
)
data class DashboardTransactionEntity(
    val competenceYear: Int,
    val competenceMonth: Int,
    val orderIndex: Int,
    val ticker: String,
    val type: String,
    val transactionYear: Int,
    val transactionMonth: Int,
    val transactionDay: Int,
    val quantity: Int,
    val unitPriceCents: Long,
    val operationVolumeCents: Long,
    val realizedResultCents: Long?,
    val accumulatedMonthlyVolumeCents: Long?,
)
