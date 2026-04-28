package com.jefisu.portlens.designsystem.components.progress

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jefisu.portlens.designsystem.PortLensTheme

@Composable
fun PortLensProgressBar(
    progress: Float,
    indicatorColor: Color,
    modifier: Modifier = Modifier,
    trackColor: Color = PortLensTheme.colors.bgSurface3,
    height: Dp = 6.dp,
) {
    val clamped = progress.coerceIn(0f, 1f)
    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(height),
    ) {
        val radius = CornerRadius(size.height, size.height)
        drawRoundRect(color = trackColor, cornerRadius = radius)
        drawRoundRect(
            color = indicatorColor,
            cornerRadius = radius,
            size = size.copy(width = size.width * clamped),
        )
    }
}
