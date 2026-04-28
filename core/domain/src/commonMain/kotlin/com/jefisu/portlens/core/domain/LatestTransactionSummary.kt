package com.jefisu.portlens.core.domain

data class LatestTransactionSummary(
    val ticker: String,
    val type: TransactionType,
    val date: TransactionDate,
    val quantity: Int,
    val unitPrice: Money,
    val operationVolume: Money,
    val realizedResult: Money?,
    val accumulatedMonthlyVolume: Money?,
)
