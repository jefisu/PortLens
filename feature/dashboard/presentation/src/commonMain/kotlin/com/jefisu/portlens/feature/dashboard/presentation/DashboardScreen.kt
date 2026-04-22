package com.jefisu.portlens.feature.dashboard.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jefisu.portlens.core.domain.CompetenceMonth
import com.jefisu.portlens.core.domain.GetDashboardSnapshot
import com.jefisu.portlens.core.presentation.isWebPlatform
import com.jefisu.portlens.designsystem.PortLensTheme
import com.jefisu.portlens.feature.dashboard.presentation.components.header.DashboardPageHeader
import com.jefisu.portlens.feature.dashboard.presentation.components.hero.DashboardHeroSection
import com.jefisu.portlens.feature.dashboard.presentation.components.table.LatestTransactionsTable
import com.jefisu.portlens.feature.dashboard.presentation.generated.resources.Res
import com.jefisu.portlens.feature.dashboard.presentation.generated.resources.dashboard_generic_error
import com.jefisu.portlens.feature.dashboard.presentation.generated.resources.dashboard_retry
import com.jefisu.portlens.feature.dashboard.presentation.util.dashboardSnapshotPreview
import org.jetbrains.compose.resources.stringResource

@Composable
fun DashboardRoot(
    selectedCompetence: CompetenceMonth,
    getDashboardSnapshot: GetDashboardSnapshot,
    modifier: Modifier = Modifier,
    onViewAllTransactions: () -> Unit = {},
    viewModel: DashboardViewModel = viewModel(key = selectedCompetence.toString()) {
        DashboardViewModel(
            initialCompetence = selectedCompetence,
            getDashboardSnapshot = getDashboardSnapshot,
        )
    },
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(selectedCompetence) {
        viewModel.setCompetence(selectedCompetence)
    }

    DashboardScreen(
        state = state,
        onAction = { action ->
            when (action) {
                DashboardAction.OnViewAllTransactionsClick -> onViewAllTransactions()
                else -> viewModel.onAction(action)
            }
        },
        modifier = modifier,
    )
}

@Composable
fun DashboardScreen(
    state: DashboardState,
    modifier: Modifier = Modifier,
    onAction: (DashboardAction) -> Unit = {},
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(PortLensTheme.colors.bgBase),
    ) {
        val hasLoadedContent = state.exemptionCard != null &&
            state.realizedGainCard != null &&
            state.estimatedTaxCard != null

        if (state.isLoading && !hasLoadedContent) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = PortLensTheme.colors.accent,
            )
        } else if (state.error != null) {
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Text(
                    text = state.error.takeUnless { it.isBlank() }
                        ?: stringResource(Res.string.dashboard_generic_error),
                    color = PortLensTheme.colors.textPrimary,
                    style = PortLensTheme.typography.bodyMd,
                )
                Text(
                    text = stringResource(Res.string.dashboard_retry),
                    color = PortLensTheme.colors.accent,
                    style = PortLensTheme.typography.bodySm,
                    modifier = Modifier
                        .background(PortLensTheme.colors.bgSurface)
                        .clickable { onAction(DashboardAction.OnRetryClick) }
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                )
            }
        } else {
            BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
                val useWebScrolling = isWebPlatform()
                val verticalSpacing = if (maxWidth < 1200.dp) 14.dp else 16.dp

                if (useWebScrolling) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy(verticalSpacing),
                    ) {
                        DashboardPageHeader(updatedAtLabel = state.updatedAtLabel)
                        DashboardHeroSection(
                            exemptionCard = requireNotNull(state.exemptionCard),
                            realizedGainCard = requireNotNull(state.realizedGainCard),
                            estimatedTaxCard = requireNotNull(state.estimatedTaxCard),
                            month = state.competenceMonth,
                        )
                        LatestTransactionsTable(
                            transactions = state.latestTransactions,
                            month = state.competenceMonth,
                            isLoading = state.isLoading,
                            onViewAllClick = {
                                onAction(DashboardAction.OnViewAllTransactionsClick)
                            },
                        )
                    }
                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(verticalSpacing),
                    ) {
                        DashboardPageHeader(updatedAtLabel = state.updatedAtLabel)
                        DashboardHeroSection(
                            exemptionCard = requireNotNull(state.exemptionCard),
                            realizedGainCard = requireNotNull(state.realizedGainCard),
                            estimatedTaxCard = requireNotNull(state.estimatedTaxCard),
                            month = state.competenceMonth,
                        )
                        LatestTransactionsTable(
                            transactions = state.latestTransactions,
                            month = state.competenceMonth,
                            isLoading = state.isLoading,
                            onViewAllClick = {
                                onAction(DashboardAction.OnViewAllTransactionsClick)
                            },
                            modifier = Modifier.weight(1f),
                        )
                    }
                }
            }
        }
    }
}

@Preview(widthDp = 1280, heightDp = 900)
@Composable
private fun DashboardScreenWidePreview() {
    PortLensTheme {
        DashboardScreen(
            state = dashboardSnapshotPreview.toDashboardState(),
            onAction = {},
        )
    }
}

@Preview(widthDp = 760, heightDp = 900)
@Composable
private fun DashboardScreenMediumPreview() {
    PortLensTheme {
        DashboardScreen(
            state = dashboardSnapshotPreview.toDashboardState(),
            onAction = {},
        )
    }
}
