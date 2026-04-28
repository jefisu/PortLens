package com.jefisu.portlens.feature.dashboard.presentation.components.header

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jefisu.portlens.designsystem.PortLensTheme
import com.jefisu.portlens.feature.dashboard.presentation.generated.resources.Res
import com.jefisu.portlens.feature.dashboard.presentation.generated.resources.dashboard_title
import com.jefisu.portlens.feature.dashboard.presentation.generated.resources.dashboard_updated_at
import org.jetbrains.compose.resources.stringResource

@Composable
fun DashboardPageHeader(updatedAtLabel: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom,
    ) {
        Text(
            text = stringResource(Res.string.dashboard_title),
            style = PortLensTheme.typography.pageTitle,
            color = PortLensTheme.colors.textPrimary,
        )
        Text(
            text = stringResource(Res.string.dashboard_updated_at, updatedAtLabel),
            style = PortLensTheme.typography.numMd,
            color = PortLensTheme.colors.textTertiary,
        )
    }
}
