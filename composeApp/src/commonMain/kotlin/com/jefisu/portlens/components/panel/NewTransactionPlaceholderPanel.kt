package com.jefisu.portlens.components.panel

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jefisu.portlens.designsystem.PortLensTheme
import com.jefisu.portlens.generated.resources.Res
import com.jefisu.portlens.generated.resources.placeholder_panel_body
import com.jefisu.portlens.generated.resources.placeholder_panel_shortcuts
import com.jefisu.portlens.generated.resources.placeholder_panel_title
import org.jetbrains.compose.resources.stringResource

@Composable
fun NewTransactionPlaceholderPanel(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text(
            text = stringResource(Res.string.placeholder_panel_title),
            style = PortLensTheme.typography.headingMd,
            color = PortLensTheme.colors.textPrimary,
        )
        Text(
            text = stringResource(Res.string.placeholder_panel_body),
            style = PortLensTheme.typography.bodyMd,
            color = PortLensTheme.colors.textSecondary,
        )
        Text(
            text = stringResource(Res.string.placeholder_panel_shortcuts),
            style = PortLensTheme.typography.bodySm,
            color = PortLensTheme.colors.textTertiary,
        )
    }
}

@Preview
@Composable
private fun NewTransactionPlaceholderPanelPreview() {
    PortLensTheme {
        NewTransactionPlaceholderPanel()
    }
}
