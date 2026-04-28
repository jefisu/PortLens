package com.jefisu.portlens.designsystem.components.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.jefisu.portlens.designsystem.PortLensTheme
import com.jefisu.portlens.designsystem.components.common.model.SemanticTone

@Composable
fun SemanticTone.color(): Color = when (this) {
    SemanticTone.Positive -> PortLensTheme.colors.gain
    SemanticTone.Negative -> PortLensTheme.colors.loss
    SemanticTone.Warning -> PortLensTheme.colors.warning
    SemanticTone.Neutral -> PortLensTheme.colors.neutral
}

@Composable
fun SemanticTone.softColor(): Color = when (this) {
    SemanticTone.Positive -> PortLensTheme.colors.gainSoft
    SemanticTone.Negative -> PortLensTheme.colors.lossSoft
    SemanticTone.Warning -> PortLensTheme.colors.warningSoft
    SemanticTone.Neutral -> PortLensTheme.colors.neutralSoft
}
