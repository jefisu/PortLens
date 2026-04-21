package com.jefisu.portlens.feature.dashboard.presentation.components.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.jefisu.portlens.designsystem.PortLensTheme
import com.jefisu.portlens.designsystem.components.common.model.SemanticTone
import com.jefisu.portlens.feature.dashboard.presentation.DashboardMetricTone
import com.jefisu.portlens.feature.dashboard.presentation.DashboardTransactionTypeUi
import com.jefisu.portlens.feature.dashboard.presentation.generated.resources.Res
import com.jefisu.portlens.feature.dashboard.presentation.generated.resources.dashboard_status_exempt
import com.jefisu.portlens.feature.dashboard.presentation.generated.resources.dashboard_status_near_limit
import com.jefisu.portlens.feature.dashboard.presentation.generated.resources.dashboard_status_taxable
import com.jefisu.portlens.feature.dashboard.presentation.generated.resources.dashboard_type_buy
import com.jefisu.portlens.feature.dashboard.presentation.generated.resources.dashboard_type_rebuy
import com.jefisu.portlens.feature.dashboard.presentation.generated.resources.dashboard_type_sell
import org.jetbrains.compose.resources.stringResource

@Composable
fun SemanticTone.label(): String = when (this) {
    SemanticTone.Positive -> stringResource(Res.string.dashboard_status_exempt)
    SemanticTone.Warning -> stringResource(Res.string.dashboard_status_near_limit)
    SemanticTone.Negative -> stringResource(Res.string.dashboard_status_taxable)
    SemanticTone.Neutral -> stringResource(Res.string.dashboard_status_exempt)
}

@Composable
fun DashboardTransactionTypeUi.label(): String = when (this) {
    DashboardTransactionTypeUi.Buy -> stringResource(Res.string.dashboard_type_buy)
    DashboardTransactionTypeUi.Sell -> stringResource(Res.string.dashboard_type_sell)
    DashboardTransactionTypeUi.Rebuy -> stringResource(Res.string.dashboard_type_rebuy)
}

@Composable
fun DashboardMetricTone.valueColor(): Color = when (this) {
    DashboardMetricTone.Positive -> PortLensTheme.colors.gain
    DashboardMetricTone.Negative -> PortLensTheme.colors.loss
    DashboardMetricTone.Warning -> PortLensTheme.colors.warning
    DashboardMetricTone.Neutral -> PortLensTheme.colors.textPrimary
}

@Composable
fun DashboardTransactionTypeUi.resultColor(): Color = when (this) {
    DashboardTransactionTypeUi.Buy -> PortLensTheme.colors.textDisabled
    DashboardTransactionTypeUi.Sell -> PortLensTheme.colors.gain
    DashboardTransactionTypeUi.Rebuy -> PortLensTheme.colors.textDisabled
}
