package com.jefisu.portlens.feature.dashboard.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jefisu.portlens.core.presentation.isWebPlatform
import com.jefisu.portlens.designsystem.PortLensTheme
import com.jefisu.portlens.feature.dashboard.presentation.components.header.DashboardPageHeader
import com.jefisu.portlens.feature.dashboard.presentation.components.hero.DashboardHeroSection
import com.jefisu.portlens.feature.dashboard.presentation.components.table.LatestTransactionsTable
import com.jefisu.portlens.feature.dashboard.presentation.util.dashboardSnapshotPreview

@Composable
fun DashboardRoot(onViewAllTransactions: () -> Unit = {}) {
    DashboardScreen(
        state = dashboardSnapshotPreview.toDashboardState(),
        onViewAllTransactions = onViewAllTransactions,
    )
}

@Composable
private fun DashboardScreen(state: DashboardState, onViewAllTransactions: () -> Unit = {}) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(PortLensTheme.colors.bgBase),
    ) {
        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = PortLensTheme.colors.accent,
            )
        } else {
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
                        onViewAllClick = onViewAllTransactions,
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
                        onViewAllClick = onViewAllTransactions,
                        modifier = Modifier.weight(1f),
                    )
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
        )
    }
}

@Preview(widthDp = 760, heightDp = 900)
@Composable
private fun DashboardScreenMediumPreview() {
    PortLensTheme {
        DashboardScreen(
            state = dashboardSnapshotPreview.toDashboardState(),
        )
    }
}
