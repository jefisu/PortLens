package com.jefisu.portlens.designsystem.components.card

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jefisu.portlens.designsystem.PortLensDimens
import com.jefisu.portlens.designsystem.PortLensTheme
import com.jefisu.portlens.designsystem.components.card.model.MiniExemptionCardUi
import com.jefisu.portlens.designsystem.components.common.color
import com.jefisu.portlens.designsystem.components.common.model.SemanticTone
import com.jefisu.portlens.designsystem.components.progress.PortLensProgressBar
import com.jefisu.portlens.designsystem.components.shell.progress
import com.jefisu.portlens.designsystem.components.shell.statusLabel
import com.jefisu.portlens.designsystem.generated.resources.Res
import com.jefisu.portlens.designsystem.generated.resources.mini_exemption_summary
import com.jefisu.portlens.designsystem.generated.resources.mini_exemption_title
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MiniExemptionCard(card: MiniExemptionCardUi, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(PortLensDimens.cardCornerRadius),
        color = PortLensTheme.colors.bgSurface,
    ) {
        Column(
            modifier = Modifier.padding(13.dp),
        ) {
            Text(
                text = stringResource(Res.string.mini_exemption_title, card.monthLabel),
                style = PortLensTheme.typography.label,
                color = PortLensTheme.colors.textTertiary,
            )
            Text(
                text = card.soldAmountLabel,
                style = PortLensTheme.typography.numMd,
                color = PortLensTheme.colors.textPrimary,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(top = 8.dp),
            )

            val highlightColor = card.tone.color()
            PortLensProgressBar(
                progress = card.progress(),
                indicatorColor = highlightColor,
                height = 5.dp,
                modifier = Modifier.padding(top = 8.dp),
            )
            Text(
                text = stringResource(
                    Res.string.mini_exemption_summary,
                    card.usedRatioLabel,
                    card.tone.statusLabel(),
                    card.remainingLabel,
                ),
                style = PortLensTheme.typography.micro,
                color = highlightColor,
                maxLines = 1,
                softWrap = false,
                overflow = TextOverflow.Clip,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .basicMarquee(),
            )
        }
    }
}

@Preview
@Composable
private fun MiniExemptionCardPreview() {
    PortLensTheme {
        MiniExemptionCard(
            card = MiniExemptionCardUi(
                monthLabel = "Nov",
                soldAmountLabel = "R$ 12.340,00",
                usedRatio = 0.617f,
                usedRatioLabel = "61,7%",
                remainingLabel = "R$ 7.660,00",
                tone = SemanticTone.Positive,
            ),
        )
    }
}
