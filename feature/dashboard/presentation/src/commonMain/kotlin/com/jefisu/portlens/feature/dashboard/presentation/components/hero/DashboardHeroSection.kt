package com.jefisu.portlens.feature.dashboard.presentation.components.hero

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jefisu.portlens.core.domain.Money
import com.jefisu.portlens.core.presentation.formatBrl
import com.jefisu.portlens.core.presentation.formatSignedBrl
import com.jefisu.portlens.core.presentation.shortMonthLabel
import com.jefisu.portlens.core.presentation.toPercentLabel
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
import kotlin.math.roundToLong
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
    val animatedSoldAmountCents = animateLongValue(card.soldAmountCents)
    val animatedUsedRatio = animateFloatAsState(
        targetValue = card.usedRatio,
        animationSpec = tween(durationMillis = 420),
        label = "dashboard_used_ratio",
    )
    val animatedRemainingAmountCents = animateLongValue(card.remainingAmountCents)

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
                text = Money(cents = animatedSoldAmountCents).formatBrl(),
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
                    progress = animatedUsedRatio.value,
                    indicatorColor = card.status.color(),
                )

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                ) {
                    Text(
                        text = animatedUsedRatio.value.toPercentLabel(),
                        style = PortLensTheme.typography.numMd,
                        color = PortLensTheme.colors.textTertiary,
                    )
                    Text(
                        text = stringResource(
                            Res.string.dashboard_exemption_remaining,
                            Money(cents = animatedRemainingAmountCents).formatBrl(),
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
    val animatedValueAmountCents = animateLongValue(metric.valueAmountCents)

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
                text = if (metric.useSignedFormat) {
                    Money(cents = animatedValueAmountCents).formatSignedBrl()
                } else {
                    Money(cents = animatedValueAmountCents).formatBrl()
                },
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

@Composable
private fun animateLongValue(targetValue: Long): Long {
    val progress = remember { Animatable(1f) }
    var startValue by remember { mutableStateOf(targetValue) }
    var currentTarget by remember { mutableStateOf(targetValue) }

    LaunchedEffect(targetValue) {
        startValue = interpolateLong(startValue, currentTarget, progress.value)
        currentTarget = targetValue
        progress.snapTo(0f)
        progress.animateTo(1f, animationSpec = tween(durationMillis = 420))
    }

    return interpolateLong(startValue, currentTarget, progress.value)
}

private fun interpolateLong(startValue: Long, endValue: Long, progress: Float): Long {
    val delta = endValue - startValue
    return (startValue + (delta * progress.toDouble())).roundToLong()
}
