package com.jefisu.portlens.designsystem.components.shell

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.jefisu.portlens.designsystem.PortLensTheme
import com.jefisu.portlens.designsystem.components.card.model.MiniExemptionCardUi
import com.jefisu.portlens.designsystem.components.common.model.SemanticTone
import com.jefisu.portlens.designsystem.components.shell.model.ShellDestination
import com.jefisu.portlens.designsystem.generated.resources.Res
import com.jefisu.portlens.designsystem.generated.resources.ic_nav_monthly_summary
import com.jefisu.portlens.designsystem.generated.resources.ic_nav_overview
import com.jefisu.portlens.designsystem.generated.resources.ic_nav_portfolio
import com.jefisu.portlens.designsystem.generated.resources.ic_nav_transactions
import com.jefisu.portlens.designsystem.generated.resources.nav_monthly_summary
import com.jefisu.portlens.designsystem.generated.resources.nav_overview
import com.jefisu.portlens.designsystem.generated.resources.nav_portfolio
import com.jefisu.portlens.designsystem.generated.resources.nav_transactions
import com.jefisu.portlens.designsystem.generated.resources.status_exempt
import com.jefisu.portlens.designsystem.generated.resources.status_near_limit
import com.jefisu.portlens.designsystem.generated.resources.status_taxable
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun ShellDestination.label(): String = when (this) {
    ShellDestination.Overview -> stringResource(Res.string.nav_overview)
    ShellDestination.Transactions -> stringResource(Res.string.nav_transactions)
    ShellDestination.Portfolio -> stringResource(Res.string.nav_portfolio)
    ShellDestination.MonthlySummary -> stringResource(Res.string.nav_monthly_summary)
}

internal fun ShellDestination.icon(): DrawableResource = when (this) {
    ShellDestination.Overview -> Res.drawable.ic_nav_overview
    ShellDestination.Transactions -> Res.drawable.ic_nav_transactions
    ShellDestination.Portfolio -> Res.drawable.ic_nav_portfolio
    ShellDestination.MonthlySummary -> Res.drawable.ic_nav_monthly_summary
}

@Composable
internal fun SemanticTone.statusLabel(): String = when (this) {
    SemanticTone.Positive -> stringResource(Res.string.status_exempt)
    SemanticTone.Warning -> stringResource(Res.string.status_near_limit)
    SemanticTone.Negative -> stringResource(Res.string.status_taxable)
    SemanticTone.Neutral -> stringResource(Res.string.status_exempt)
}

@Composable
internal fun navItemColor(isSelected: Boolean): Color = if (isSelected) {
    PortLensTheme.colors.textPrimary
} else {
    PortLensTheme.colors.textSecondary
}

internal fun MiniExemptionCardUi.progress(): Float = usedRatio.coerceIn(0f, 1f)
