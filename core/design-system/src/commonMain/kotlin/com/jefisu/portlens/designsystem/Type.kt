package com.jefisu.portlens.designsystem

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.jefisu.portlens.core.designsystem.generated.resources.Res
import com.jefisu.portlens.core.designsystem.generated.resources.fraunces_regular
import com.jefisu.portlens.core.designsystem.generated.resources.ibm_plex_mono_medium
import com.jefisu.portlens.core.designsystem.generated.resources.ibm_plex_mono_regular
import com.jefisu.portlens.core.designsystem.generated.resources.inter_tight_medium
import com.jefisu.portlens.core.designsystem.generated.resources.inter_tight_regular
import com.jefisu.portlens.core.designsystem.generated.resources.inter_tight_semibold
import org.jetbrains.compose.resources.Font

@Composable
private fun displayFamily() = FontFamily(
    Font(Res.font.fraunces_regular, FontWeight.Normal),
)

@Composable
private fun interfaceFamily() = FontFamily(
    Font(Res.font.inter_tight_regular, FontWeight.Normal),
    Font(Res.font.inter_tight_medium, FontWeight.Medium),
    Font(Res.font.inter_tight_semibold, FontWeight.SemiBold),
)

@Composable
private fun monoFamily() = FontFamily(
    Font(Res.font.ibm_plex_mono_regular, FontWeight.Normal),
    Font(Res.font.ibm_plex_mono_medium, FontWeight.Medium),
)

@Immutable
data class PortLensTypography(
    val displayXl: TextStyle,
    val displayLg: TextStyle,
    val displayMd: TextStyle,
    val pageTitle: TextStyle,
    val headingLg: TextStyle,
    val headingMd: TextStyle,
    val bodyMd: TextStyle,
    val bodySm: TextStyle,
    val label: TextStyle,
    val micro: TextStyle,
    val numHero: TextStyle,
    val numLg: TextStyle,
    val numMd: TextStyle,
)

private const val TABULAR_NUM = "tnum"

private val tightLineHeight = LineHeightStyle(
    alignment = LineHeightStyle.Alignment.Center,
    trim = LineHeightStyle.Trim.None,
)

@Composable
internal fun portLensTypography(): PortLensTypography {
    val display = displayFamily()
    val ui = interfaceFamily()
    val mono = monoFamily()

    return PortLensTypography(
        displayXl = TextStyle(
            fontFamily = display,
            fontWeight = FontWeight.Normal,
            fontSize = 56.sp,
            lineHeight = (56 * 1.05).sp,
            letterSpacing = 0.sp,
            lineHeightStyle = tightLineHeight,
        ),
        displayLg = TextStyle(
            fontFamily = display,
            fontWeight = FontWeight.Normal,
            fontSize = 40.sp,
            lineHeight = (40 * 1.1).sp,
            lineHeightStyle = tightLineHeight,
        ),
        displayMd = TextStyle(
            fontFamily = display,
            fontWeight = FontWeight.Normal,
            fontSize = 28.sp,
            lineHeight = (28 * 1.15).sp,
            lineHeightStyle = tightLineHeight,
        ),
        pageTitle = TextStyle(
            fontFamily = display,
            fontWeight = FontWeight.Normal,
            fontSize = 40.sp,
            lineHeight = (40 * 1.1).sp,
            lineHeightStyle = tightLineHeight,
        ),
        headingLg = TextStyle(
            fontFamily = ui,
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp,
            lineHeight = (20 * 1.3).sp,
            lineHeightStyle = tightLineHeight,
        ),
        headingMd = TextStyle(
            fontFamily = ui,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            lineHeight = (16 * 1.4).sp,
            lineHeightStyle = tightLineHeight,
        ),
        bodyMd = TextStyle(
            fontFamily = ui,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            lineHeight = (14 * 1.5).sp,
            lineHeightStyle = tightLineHeight,
        ),
        bodySm = TextStyle(
            fontFamily = ui,
            fontWeight = FontWeight.Normal,
            fontSize = 13.sp,
            lineHeight = (13 * 1.5).sp,
            lineHeightStyle = tightLineHeight,
        ),
        label = TextStyle(
            fontFamily = ui,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
            lineHeight = (12 * 1.4).sp,
            letterSpacing = 0.05.em,
            lineHeightStyle = tightLineHeight,
        ),
        micro = TextStyle(
            fontFamily = ui,
            fontWeight = FontWeight.Medium,
            fontSize = 11.sp,
            lineHeight = (11 * 1.3).sp,
            letterSpacing = 0.05.em,
            lineHeightStyle = tightLineHeight,
        ),
        numHero = TextStyle(
            fontFamily = mono,
            fontWeight = FontWeight.Medium,
            fontSize = 48.sp,
            lineHeight = 48.sp,
            fontFeatureSettings = TABULAR_NUM,
            lineHeightStyle = tightLineHeight,
        ),
        numLg = TextStyle(
            fontFamily = mono,
            fontWeight = FontWeight.Medium,
            fontSize = 22.sp,
            lineHeight = (22 * 1.2).sp,
            fontFeatureSettings = TABULAR_NUM,
            lineHeightStyle = tightLineHeight,
        ),
        numMd = TextStyle(
            fontFamily = mono,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            lineHeight = (14 * 1.4).sp,
            fontFeatureSettings = TABULAR_NUM,
            lineHeightStyle = tightLineHeight,
        ),
    )
}

internal val LocalPortLensTypography = staticCompositionLocalOf<PortLensTypography> {
    error("PortLensTypography not provided. Wrap content in PortLensTheme { ... }.")
}
