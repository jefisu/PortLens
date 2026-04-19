package com.jefisu.portlens.designsystem

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
fun PortLensTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) DarkColors else LightColors
    val typography = portLensTypography()

    CompositionLocalProvider(
        LocalPortLensColors provides colors,
        LocalPortLensTypography provides typography,
    ) {
        MaterialTheme(
            colorScheme = colors.toMaterialColorScheme(),
            typography = typography.toMaterialTypography(),
            content = content,
        )
    }
}

object PortLensTheme {
    val colors: PortLensColors
        @Composable get() = LocalPortLensColors.current

    val typography: PortLensTypography
        @Composable get() = LocalPortLensTypography.current
}

private fun PortLensColors.toMaterialColorScheme(): ColorScheme {
    val base = if (isDark) darkColorScheme() else lightColorScheme()
    return base.copy(
        primary = accent,
        onPrimary = accentFg,
        secondary = neutral,
        onSecondary = textPrimary,
        background = bgBase,
        onBackground = textPrimary,
        surface = bgSurface,
        onSurface = textPrimary,
        surfaceVariant = bgSurface2,
        onSurfaceVariant = textSecondary,
        surfaceContainer = bgSurface2,
        surfaceContainerHigh = bgSurface3,
        error = loss,
        onError = accentFg,
        outline = borderDefault,
        outlineVariant = borderSubtle,
    )
}

private fun PortLensTypography.toMaterialTypography(): Typography = Typography(
    displayLarge = displayXl,
    displayMedium = displayLg,
    displaySmall = displayMd,
    headlineLarge = displayMd,
    headlineMedium = headingLg,
    headlineSmall = headingMd,
    titleLarge = headingLg,
    titleMedium = headingMd,
    titleSmall = bodyMd,
    bodyLarge = bodyMd,
    bodyMedium = bodyMd,
    bodySmall = bodySm,
    labelLarge = label,
    labelMedium = label,
    labelSmall = micro,
)
