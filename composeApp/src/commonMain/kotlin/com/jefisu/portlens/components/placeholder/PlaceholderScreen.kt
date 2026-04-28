package com.jefisu.portlens.components.placeholder

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jefisu.portlens.designsystem.PortLensTheme

@Composable
fun PlaceholderScreen(title: String, description: String, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.fillMaxSize(),
        shape = RoundedCornerShape(28.dp),
        color = PortLensTheme.colors.bgSurface,
    ) {
        Column(
            modifier = Modifier.padding(28.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Text(
                text = title,
                style = PortLensTheme.typography.displayMd,
                color = PortLensTheme.colors.textPrimary,
            )
            Text(
                text = description,
                style = PortLensTheme.typography.bodyMd,
                color = PortLensTheme.colors.textSecondary,
            )
        }
    }
}

@Preview
@Composable
private fun PlaceholderScreenPreview() {
    PortLensTheme {
        PlaceholderScreen(
            title = "Carteira atual",
            description =
                "A visão consolidada de carteira entra depois do ledger manual estar estável.",
        )
    }
}
