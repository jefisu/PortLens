package com.jefisu.portlens.feature.dashboard.presentation

import com.jefisu.portlens.core.domain.DashboardSnapshot
import com.jefisu.portlens.core.domain.ExemptionStatus
import com.jefisu.portlens.core.domain.TransactionType
import com.jefisu.portlens.core.presentation.formatBrl
import com.jefisu.portlens.core.presentation.formatShortDate
import com.jefisu.portlens.core.presentation.formatSignedBrl
import com.jefisu.portlens.core.presentation.formatUpdatedAt
import com.jefisu.portlens.designsystem.components.common.model.SemanticTone
import kotlin.math.roundToInt

fun DashboardSnapshot.toDashboardState(): DashboardState = DashboardState(
    isLoading = false,
    updatedAtLabel = lastUpdatedAt.formatUpdatedAt(),
    competenceMonth = competence.month,
    exemptionCard = DashboardExemptionCardUi(
        status = exemptionStatus.toSemanticTone(),
        soldAmountLabel = totalSoldInMonth.formatBrl(),
        limitAmountLabel = monthlyExemptionLimit.formatBrl(),
        usedRatio = usedLimitRatio,
        usedRatioLabel = usedLimitRatio.toPercentLabel(),
        remainingAmountLabel = remainingExemptionMargin.formatBrl(),
    ),
    realizedGainCard = DashboardSummaryMetricUi(
        valueLabel = realizedGainInMonth.formatSignedBrl(),
        supportingLabel = "2",
        tone = realizedGainInMonth.toResultTone(),
    ),
    estimatedTaxCard = DashboardSummaryMetricUi(
        valueLabel = estimatedTaxInMonth.formatBrl(),
        supportingLabel = "",
        tone = if (estimatedTaxInMonth.cents >
            0
        ) {
            DashboardMetricTone.Warning
        } else {
            DashboardMetricTone.Neutral
        },
    ),
    latestTransactions = latestTransactions.map { transaction ->
        LatestTransactionUi(
            dateLabel = transaction.date.formatShortDate().dropLast(5),
            ticker = transaction.ticker,
            type = when (transaction.type) {
                TransactionType.Buy -> DashboardTransactionTypeUi.Buy
                TransactionType.Sell -> DashboardTransactionTypeUi.Sell
                TransactionType.Rebuy -> DashboardTransactionTypeUi.Rebuy
            },
            quantityLabel = transaction.quantity.toString(),
            unitPriceLabel = transaction.unitPrice.formatBrl(),
            volumeLabel = transaction.operationVolume.formatBrl(),
            resultLabel = transaction.realizedResult?.formatSignedBrl(),
            accumulatedMonthlyVolumeLabel = transaction.accumulatedMonthlyVolume?.formatBrl(),
        )
    },
)

private fun ExemptionStatus.toSemanticTone(): SemanticTone = when (this) {
    ExemptionStatus.Exempt -> SemanticTone.Positive
    ExemptionStatus.NearLimit -> SemanticTone.Warning
    ExemptionStatus.Taxable -> SemanticTone.Negative
}

private fun com.jefisu.portlens.core.domain.Money.toResultTone(): DashboardMetricTone = when {
    cents > 0 -> DashboardMetricTone.Positive
    cents < 0 -> DashboardMetricTone.Negative
    else -> DashboardMetricTone.Neutral
}

private fun Float.toPercentLabel(): String {
    val scaled = (this * 1000).roundToInt() / 10f
    val text = scaled.toString().replace('.', ',')
    return if (text.contains(',')) "$text%" else "$text,0%"
}
