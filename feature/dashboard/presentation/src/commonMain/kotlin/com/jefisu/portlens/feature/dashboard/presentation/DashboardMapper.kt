package com.jefisu.portlens.feature.dashboard.presentation

import com.jefisu.portlens.core.domain.DashboardSnapshot
import com.jefisu.portlens.core.domain.ExemptionStatus
import com.jefisu.portlens.core.domain.TransactionType
import com.jefisu.portlens.core.presentation.formatBrl
import com.jefisu.portlens.core.presentation.formatShortDate
import com.jefisu.portlens.core.presentation.formatSignedBrl
import com.jefisu.portlens.core.presentation.formatUpdatedAt
import com.jefisu.portlens.core.presentation.isCurrentLocalCompetence
import com.jefisu.portlens.designsystem.components.common.model.SemanticTone

fun DashboardSnapshot.toDashboardState(): DashboardState = DashboardState(
    isLoading = false,
    error = null,
    updatedAtLabel = lastUpdatedAt.formatUpdatedAt(),
    competenceMonth = competence.month,
    exemptionCard = DashboardExemptionCardUi(
        status = exemptionStatus.toSemanticTone(competence.isCurrentLocalCompetence()),
        soldAmountCents = totalSoldInMonth.cents,
        limitAmountLabel = monthlyExemptionLimit.formatBrl(),
        usedRatio = usedLimitRatio,
        remainingAmountCents = remainingExemptionMargin.cents,
    ),
    realizedGainCard = DashboardSummaryMetricUi(
        valueAmountCents = realizedGainInMonth.cents,
        supportingLabel = latestTransactions.count { it.type == TransactionType.Sell }.toString(),
        tone = realizedGainInMonth.toResultTone(),
        useSignedFormat = true,
    ),
    estimatedTaxCard = DashboardSummaryMetricUi(
        valueAmountCents = estimatedTaxInMonth.cents,
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

private fun ExemptionStatus.toSemanticTone(isCurrentCompetence: Boolean): SemanticTone =
    when (this) {
        ExemptionStatus.Exempt -> SemanticTone.Positive

        ExemptionStatus.NearLimit -> if (isCurrentCompetence) {
            SemanticTone.Warning
        } else {
            SemanticTone.Positive
        }

        ExemptionStatus.Taxable -> SemanticTone.Negative
    }

private fun com.jefisu.portlens.core.domain.Money.toResultTone(): DashboardMetricTone = when {
    cents > 0 -> DashboardMetricTone.Positive
    cents < 0 -> DashboardMetricTone.Negative
    else -> DashboardMetricTone.Neutral
}
