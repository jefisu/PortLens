package com.jefisu.portlens.feature.dashboard.presentation

import androidx.compose.runtime.Immutable
import com.jefisu.portlens.designsystem.components.common.model.SemanticTone
import kotlinx.datetime.Month

@Immutable
data class DashboardState(
    val isLoading: Boolean = true,
    val updatedAtLabel: String = "",
    val competenceMonth: Month = Month.JANUARY,
    val exemptionCard: DashboardExemptionCardUi? = null,
    val realizedGainCard: DashboardSummaryMetricUi? = null,
    val estimatedTaxCard: DashboardSummaryMetricUi? = null,
    val latestTransactions: List<LatestTransactionUi> = emptyList(),
)

data class DashboardExemptionCardUi(
    val status: SemanticTone,
    val soldAmountLabel: String,
    val limitAmountLabel: String,
    val usedRatio: Float,
    val usedRatioLabel: String,
    val remainingAmountLabel: String,
)

data class DashboardSummaryMetricUi(
    val valueLabel: String,
    val supportingLabel: String,
    val tone: DashboardMetricTone,
)

data class LatestTransactionUi(
    val dateLabel: String,
    val ticker: String,
    val type: DashboardTransactionTypeUi,
    val quantityLabel: String,
    val unitPriceLabel: String,
    val volumeLabel: String,
    val resultLabel: String?,
    val accumulatedMonthlyVolumeLabel: String?,
)

enum class DashboardMetricTone {
    Positive,
    Negative,
    Warning,
    Neutral,
}

enum class DashboardTransactionTypeUi {
    Buy,
    Sell,
    Rebuy,
}
