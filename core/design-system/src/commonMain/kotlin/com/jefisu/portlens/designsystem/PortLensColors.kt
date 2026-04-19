package com.jefisu.portlens.designsystem

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class PortLensColors(
    val bgBase: Color,
    val bgSurface: Color,
    val bgSurface2: Color,
    val bgSurface3: Color,
    val borderSubtle: Color,
    val borderDefault: Color,
    val borderStrong: Color,
    val textPrimary: Color,
    val textSecondary: Color,
    val textTertiary: Color,
    val textDisabled: Color,
    val gain: Color,
    val gainSoft: Color,
    val loss: Color,
    val lossSoft: Color,
    val warning: Color,
    val warningSoft: Color,
    val neutral: Color,
    val neutralSoft: Color,
    val accent: Color,
    val accentHover: Color,
    val accentFg: Color,
    val isDark: Boolean,
)

internal val DarkColors = PortLensColors(
    bgBase = DarkPalette.BgBase,
    bgSurface = DarkPalette.BgSurface,
    bgSurface2 = DarkPalette.BgSurface2,
    bgSurface3 = DarkPalette.BgSurface3,
    borderSubtle = DarkPalette.BorderSubtle,
    borderDefault = DarkPalette.BorderDefault,
    borderStrong = DarkPalette.BorderStrong,
    textPrimary = DarkPalette.TextPrimary,
    textSecondary = DarkPalette.TextSecondary,
    textTertiary = DarkPalette.TextTertiary,
    textDisabled = DarkPalette.TextDisabled,
    gain = DarkPalette.Gain,
    gainSoft = DarkPalette.GainSoft,
    loss = DarkPalette.Loss,
    lossSoft = DarkPalette.LossSoft,
    warning = DarkPalette.Warning,
    warningSoft = DarkPalette.WarningSoft,
    neutral = DarkPalette.Neutral,
    neutralSoft = DarkPalette.NeutralSoft,
    accent = DarkPalette.Accent,
    accentHover = DarkPalette.AccentHover,
    accentFg = DarkPalette.AccentFg,
    isDark = true,
)

internal val LightColors = PortLensColors(
    bgBase = LightPalette.BgBase,
    bgSurface = LightPalette.BgSurface,
    bgSurface2 = LightPalette.BgSurface2,
    bgSurface3 = LightPalette.BgSurface3,
    borderSubtle = LightPalette.BorderSubtle,
    borderDefault = LightPalette.BorderDefault,
    borderStrong = LightPalette.BorderStrong,
    textPrimary = LightPalette.TextPrimary,
    textSecondary = LightPalette.TextSecondary,
    textTertiary = LightPalette.TextTertiary,
    textDisabled = LightPalette.TextDisabled,
    gain = LightPalette.Gain,
    gainSoft = LightPalette.GainSoft,
    loss = LightPalette.Loss,
    lossSoft = LightPalette.LossSoft,
    warning = LightPalette.Warning,
    warningSoft = LightPalette.WarningSoft,
    neutral = LightPalette.Neutral,
    neutralSoft = LightPalette.NeutralSoft,
    accent = LightPalette.Accent,
    accentHover = LightPalette.AccentHover,
    accentFg = LightPalette.AccentFg,
    isDark = false,
)

internal val LocalPortLensColors = staticCompositionLocalOf<PortLensColors> {
    error("PortLensColors not provided. Wrap content in PortLensTheme { ... }.")
}
