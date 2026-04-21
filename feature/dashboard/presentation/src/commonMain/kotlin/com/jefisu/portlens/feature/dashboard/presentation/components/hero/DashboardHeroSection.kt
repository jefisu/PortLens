package com.jefisu.portlens.feature.dashboard.presentation.components.hero

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jefisu.portlens.core.presentation.shortMonthLabel
import com.jefisu.portlens.designsystem.PortLensDimens
import com.jefisu.portlens.designsystem.PortLensTheme
import com.jefisu.portlens.designsystem.components.badge.PortLensBadge
import com.jefisu.portlens.designsystem.components.common.color
import com.jefisu.portlens.designsystem.components.common.model.SemanticTone
import com.jefisu.portlens.designsystem.components.common.softColor
import com.jefisu.portlens.designsystem.components.progress.PortLensProgressBar
import com.jefisu.portlens.feature.dashboard.presentation.DashboardExemptionCardUi
import com.jefisu.portlens.feature.dashboard.presentation.DashboardSummaryMetricUi
import com.jefisu.portlens.feature.dashboard.presentation.components.common.label
import com.jefisu.portlens.feature.dashboard.presentation.components.common.valueColor
import com.jefisu.portlens.feature.dashboard.presentation.generated.resources.Res
import com.jefisu.portlens.feature.dashboard.presentation.generated.resources.dashboard_estimated_tax
import com.jefisu.portlens.feature.dashboard.presentation.generated.resources.dashboard_estimated_tax_supporting
import com.jefisu.portlens.feature.dashboard.presentation.generated.resources.dashboard_exemption_hint
import com.jefisu.portlens.feature.dashboard.presentation.generated.resources.dashboard_exemption_remaining
import com.jefisu.portlens.feature.dashboard.presentation.generated.resources.dashboard_exemption_subtitle
import com.jefisu.portlens.feature.dashboard.presentation.generated.resources.dashboard_exemption_title
import com.jefisu.portlens.feature.dashboard.presentation.generated.resources.dashboard_realized_gain
import com.jefisu.portlens.feature.dashboard.presentation.generated.resources.dashboard_realized_gain_supporting
import kotlinx.datetime.Month
import org.jetbrains.compose.resources.stringResource

@Composable
fun DashboardHeroSection(
    exemptionCard: DashboardExemptionCardUi,
    realizedGainCard: DashboardSummaryMetricUi,
    estimatedTaxCard: DashboardSummaryMetricUi,
    month: Month,
    modifier: Modifier = Modifier,
) {
    val monthLabel = month.shortMonthLabel()
    val summaryCardsSpacing = 12.dp

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
        horizontalArrangement = Arrangement.spacedBy(PortLensDimens.sectionSpacing),
    ) {
        ExemptionHeroCard(
            card = exemptionCard,
            modifier = Modifier
                .weight(2f),
        )

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(summaryCardsSpacing),
        ) {
            SummaryMetricCard(
                title = stringResource(Res.string.dashboard_realized_gain, monthLabel),
                metric = realizedGainCard,
                supportingLabel = if (realizedGainCard.supportingLabel.isNotBlank()) {
                    stringResource(
                        Res.string.dashboard_realized_gain_supporting,
                        realizedGainCard.supportingLabel,
                    )
                } else {
                    ""
                },
                modifier = Modifier.weight(1f),
            )
            SummaryMetricCard(
                title = stringResource(Res.string.dashboard_estimated_tax, monthLabel),
                metric = estimatedTaxCard,
                supportingLabel = stringResource(
                    Res.string.dashboard_estimated_tax_supporting,
                ),
                modifier = Modifier.weight(1f),
            )
        }
    }
}

@Composable
private fun ExemptionHeroCard(card: DashboardExemptionCardUi, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(PortLensDimens.cardCornerRadius),
        color = PortLensTheme.colors.bgSurface,
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top,
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
                    Text(
                        text = stringResource(Res.string.dashboard_exemption_title).uppercase(),
                        style = PortLensTheme.typography.label,
                        color = PortLensTheme.colors.textTertiary,
                    )
                    Text(
                        text = stringResource(Res.string.dashboard_exemption_subtitle).uppercase(),
                        style = PortLensTheme.typography.micro,
                        color = PortLensTheme.colors.textTertiary,
                    )
                }

                DashboardStatusBadge(status = card.status)
            }

            Text(
                text = card.soldAmountLabel,
                style = PortLensTheme.typography.numHero,
                color = PortLensTheme.colors.textPrimary,
            )

            Text(
                text = stringResource(Res.string.dashboard_exemption_hint, card.limitAmountLabel),
                style = PortLensTheme.typography.bodySm,
                color = PortLensTheme.colors.textSecondary,
            )

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                PortLensProgressBar(
                    progress = card.usedRatio,
                    indicatorColor = card.status.color(),
                )

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                ) {
                    Text(
                        text = card.usedRatioLabel,
                        style = PortLensTheme.typography.numMd,
                        color = PortLensTheme.colors.textTertiary,
                    )
                    Text(
                        text = stringResource(
                            Res.string.dashboard_exemption_remaining,
                            card.remainingAmountLabel,
                        ),
                        style = PortLensTheme.typography.bodySm,
                        color = card.status.color(),
                        maxLines = 2,
                    )
                }
            }
        }
    }
}

@Composable
private fun DashboardStatusBadge(status: SemanticTone) {
    PortLensBadge(
        text = status.label(),
        containerColor = status.softColor(),
        contentColor = status.color(),
        leadingDotColor = status.color(),
    )
}

@Composable
private fun SummaryMetricCard(
    title: String,
    metric: DashboardSummaryMetricUi,
    supportingLabel: String,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(PortLensDimens.cardCornerRadius),
        color = PortLensTheme.colors.bgSurface,
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(20.dp),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = title.uppercase(),
                style = PortLensTheme.typography.label,
                color = PortLensTheme.colors.textTertiary,
            )
            Text(
                text = metric.valueLabel,
                style = PortLensTheme.typography.numLg,
                color = metric.tone.valueColor(),
            )
            Text(
                text = supportingLabel,
                style = PortLensTheme.typography.bodySm,
                color = PortLensTheme.colors.textTertiary,
            )
        }
    }
}
