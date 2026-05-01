package com.jefisu.portlens.core.database.mapper

import com.jefisu.portlens.core.database.entity.CompetenceRecord
import com.jefisu.portlens.core.database.entity.DashboardSnapshotEntity
import com.jefisu.portlens.core.database.entity.DashboardTransactionEntity
import com.jefisu.portlens.core.domain.CompetenceMonth
import com.jefisu.portlens.core.domain.DashboardSnapshot
import com.jefisu.portlens.core.domain.ExemptionStatus
import com.jefisu.portlens.core.domain.LatestTransactionSummary
import com.jefisu.portlens.core.domain.Money
import com.jefisu.portlens.core.domain.Timestamp
import com.jefisu.portlens.core.domain.TransactionDate
import com.jefisu.portlens.core.domain.TransactionType
import kotlinx.datetime.Month

fun DashboardSnapshot.toEntity(): DashboardSnapshotEntity = DashboardSnapshotEntity(
    competenceYear = competence.year,
    competenceMonth = competence.month.ordinal + 1,
    lastUpdatedYear = lastUpdatedAt.year,
    lastUpdatedMonth = lastUpdatedAt.month,
    lastUpdatedDay = lastUpdatedAt.day,
    lastUpdatedHour = lastUpdatedAt.hour,
    lastUpdatedMinute = lastUpdatedAt.minute,
    monthlyExemptionLimitCents = monthlyExemptionLimit.cents,
    totalSoldInMonthCents = totalSoldInMonth.cents,
    remainingExemptionMarginCents = remainingExemptionMargin.cents,
    realizedGainInMonthCents = realizedGainInMonth.cents,
    estimatedTaxInMonthCents = estimatedTaxInMonth.cents,
    exemptionStatus = exemptionStatus.name,
    usedLimitRatio = usedLimitRatio,
)

fun DashboardSnapshot.toTransactionEntities(): List<DashboardTransactionEntity> =
    latestTransactions.mapIndexed { index, transaction ->
        DashboardTransactionEntity(
            competenceYear = competence.year,
            competenceMonth = competence.month.ordinal + 1,
            orderIndex = index,
            ticker = transaction.ticker,
            type = transaction.type.name,
            transactionYear = transaction.date.year,
            transactionMonth = transaction.date.month,
            transactionDay = transaction.date.day,
            quantity = transaction.quantity,
            unitPriceCents = transaction.unitPrice.cents,
            operationVolumeCents = transaction.operationVolume.cents,
            realizedResultCents = transaction.realizedResult?.cents,
            accumulatedMonthlyVolumeCents = transaction.accumulatedMonthlyVolume?.cents,
        )
    }

fun CompetenceRecord.toCompetenceMonth(): CompetenceMonth = CompetenceMonth(
    year = competenceYear,
    month = Month.entries[competenceMonth - 1],
)

fun DashboardSnapshotEntity.toDomain(transactions: List<DashboardTransactionEntity>): DashboardSnapshot =
    DashboardSnapshot(
        competence = CompetenceMonth(
            year = competenceYear,
            month = Month.entries[competenceMonth - 1],
        ),
        lastUpdatedAt = Timestamp(
            year = lastUpdatedYear,
            month = lastUpdatedMonth,
            day = lastUpdatedDay,
            hour = lastUpdatedHour,
            minute = lastUpdatedMinute,
        ),
        monthlyExemptionLimit = Money(monthlyExemptionLimitCents),
        totalSoldInMonth = Money(totalSoldInMonthCents),
        remainingExemptionMargin = Money(remainingExemptionMarginCents),
        realizedGainInMonth = Money(realizedGainInMonthCents),
        estimatedTaxInMonth = Money(estimatedTaxInMonthCents),
        exemptionStatus = ExemptionStatus.valueOf(exemptionStatus),
        usedLimitRatio = usedLimitRatio,
        latestTransactions = transactions.map { it.toDomain() },
    )

fun DashboardTransactionEntity.toDomain(): LatestTransactionSummary = LatestTransactionSummary(
    ticker = ticker,
    type = TransactionType.valueOf(type),
    date = TransactionDate(
        year = transactionYear,
        month = transactionMonth,
        day = transactionDay,
    ),
    quantity = quantity,
    unitPrice = Money(unitPriceCents),
    operationVolume = Money(operationVolumeCents),
    realizedResult = realizedResultCents?.let(::Money),
    accumulatedMonthlyVolume = accumulatedMonthlyVolumeCents?.let(::Money),
)
