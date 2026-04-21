package com.jefisu.portlens

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.jefisu.portlens.components.panel.NewTransactionPlaceholderPanel
import com.jefisu.portlens.components.placeholder.PlaceholderScreen
import com.jefisu.portlens.core.domain.ExemptionStatus
import com.jefisu.portlens.core.presentation.formatBrl
import com.jefisu.portlens.core.presentation.formatCompetence
import com.jefisu.portlens.core.presentation.shortMonthLabel
import com.jefisu.portlens.designsystem.PortLensTheme
import com.jefisu.portlens.designsystem.components.card.model.MiniExemptionCardUi
import com.jefisu.portlens.designsystem.components.common.model.SemanticTone
import com.jefisu.portlens.designsystem.components.shell.PortLensAppShell
import com.jefisu.portlens.designsystem.components.shell.model.ShellDestination
import com.jefisu.portlens.designsystem.components.shell.model.ShellNavItemUi
import com.jefisu.portlens.feature.dashboard.presentation.DashboardRoot
import com.jefisu.portlens.feature.dashboard.presentation.util.dashboardSnapshotPreview
import com.jefisu.portlens.generated.resources.Res
import com.jefisu.portlens.generated.resources.placeholder_portfolio_body
import com.jefisu.portlens.generated.resources.placeholder_portfolio_title
import com.jefisu.portlens.generated.resources.placeholder_summary_body
import com.jefisu.portlens.generated.resources.placeholder_summary_title
import com.jefisu.portlens.generated.resources.placeholder_transactions_body
import com.jefisu.portlens.generated.resources.placeholder_transactions_title
import org.jetbrains.compose.resources.stringResource

@Composable
fun App() {
    PortLensTheme {
        PortLensApp()
    }
}

@Composable
private fun PortLensApp() {
    val snapshot = dashboardSnapshotPreview
    var isTransactionPanelOpen by remember { mutableStateOf(false) }
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination

    val navItems = listOf(
        ShellNavItemUi(
            destination = ShellDestination.Overview,
            isSelected = currentDestination?.hasRoute<AppRoute.Dashboard>() == true,
        ),
        ShellNavItemUi(
            destination = ShellDestination.Transactions,
            isSelected = currentDestination?.hasRoute<AppRoute.Transactions>() == true,
        ),
        ShellNavItemUi(
            destination = ShellDestination.Portfolio,
            isSelected = currentDestination?.hasRoute<AppRoute.CurrentPortfolio>() == true,
        ),
        ShellNavItemUi(
            destination = ShellDestination.MonthlySummary,
            isSelected = currentDestination?.hasRoute<AppRoute.MonthlySummary>() == true,
        ),
    )

    PortLensAppShell(
        competenceLabel = snapshot.competence.formatCompetence(),
        navItems = navItems,
        miniExemptionCard = MiniExemptionCardUi(
            monthLabel = snapshot.competence.month.shortMonthLabel(),
            soldAmountLabel = snapshot.totalSoldInMonth.formatBrl(),
            usedRatio = snapshot.usedLimitRatio,
            usedRatioLabel = "61,7%",
            remainingLabel = snapshot.remainingExemptionMargin.formatBrl(),
            tone = snapshot.exemptionStatus.toSemanticTone(),
        ),
        isTransactionPanelOpen = isTransactionPanelOpen,
        onNavClick = { destination ->
            val route = when (destination) {
                ShellDestination.Overview -> AppRoute.Dashboard
                ShellDestination.Transactions -> AppRoute.Transactions
                ShellDestination.Portfolio -> AppRoute.CurrentPortfolio
                ShellDestination.MonthlySummary -> AppRoute.MonthlySummary
            }

            navController.navigate(route) {
                launchSingleTop = true
                popUpTo<AppRoute.Dashboard>()
            }
        },
        onCompetenceClick = { /* TODO: open competence picker */ },
        onNewTransactionClick = { isTransactionPanelOpen = true },
        onClosePanel = { isTransactionPanelOpen = false },
        onOpenPanel = { isTransactionPanelOpen = true },
        panelContent = { NewTransactionPlaceholderPanel() },
        content = {
            NavHost(
                navController = navController,
                startDestination = AppRoute.Dashboard,
                enterTransition = {
                    slideInVertically(
                        initialOffsetY = { fullHeight -> fullHeight / 4 },
                        animationSpec = tween(durationMillis = 260),
                    ) + fadeIn(animationSpec = tween(durationMillis = 180))
                },
                exitTransition = {
                    slideOutVertically(
                        targetOffsetY = { fullHeight -> -(fullHeight / 4) },
                        animationSpec = tween(durationMillis = 260),
                    ) + fadeOut(animationSpec = tween(durationMillis = 140))
                },
                popEnterTransition = {
                    slideInVertically(
                        initialOffsetY = { fullHeight -> fullHeight / 4 },
                        animationSpec = tween(durationMillis = 260),
                    ) + fadeIn(animationSpec = tween(durationMillis = 180))
                },
                popExitTransition = {
                    slideOutVertically(
                        targetOffsetY = { fullHeight -> -(fullHeight / 4) },
                        animationSpec = tween(durationMillis = 260),
                    ) + fadeOut(animationSpec = tween(durationMillis = 140))
                },
                modifier = Modifier
                    .fillMaxSize()
                    .background(PortLensTheme.colors.bgBase),
            ) {
                composable<AppRoute.Dashboard> {
                    DashboardRoot(
                        onViewAllTransactions = {
                            navController.navigate(AppRoute.Transactions) {
                                launchSingleTop = true
                                popUpTo<AppRoute.Dashboard>()
                            }
                        },
                    )
                }

                composable<AppRoute.Transactions> {
                    PlaceholderScreen(
                        title = stringResource(Res.string.placeholder_transactions_title),
                        description = stringResource(Res.string.placeholder_transactions_body),
                    )
                }

                composable<AppRoute.CurrentPortfolio> {
                    PlaceholderScreen(
                        title = stringResource(Res.string.placeholder_portfolio_title),
                        description = stringResource(Res.string.placeholder_portfolio_body),
                    )
                }

                composable<AppRoute.MonthlySummary> {
                    PlaceholderScreen(
                        title = stringResource(Res.string.placeholder_summary_title),
                        description = stringResource(Res.string.placeholder_summary_body),
                    )
                }
            }
        },
    )
}

@Preview
@Composable
private fun AppPreview() {
    PortLensTheme {
        PortLensApp()
    }
}

private fun ExemptionStatus.toSemanticTone(): SemanticTone = when (this) {
    ExemptionStatus.Exempt -> SemanticTone.Positive
    ExemptionStatus.NearLimit -> SemanticTone.Warning
    ExemptionStatus.Taxable -> SemanticTone.Negative
}
