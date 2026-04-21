package com.jefisu.portlens.designsystem.components.shell

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jefisu.portlens.designsystem.PortLensTheme
import com.jefisu.portlens.designsystem.components.shell.model.ShellDestination
import com.jefisu.portlens.designsystem.components.shell.model.ShellNavItemUi
import com.jefisu.portlens.designsystem.generated.resources.Res
import com.jefisu.portlens.designsystem.generated.resources.app_name
import com.jefisu.portlens.designsystem.generated.resources.competence_label
import com.jefisu.portlens.designsystem.generated.resources.ic_chevron_down
import com.jefisu.portlens.designsystem.generated.resources.ic_portlens_mark
import com.jefisu.portlens.designsystem.generated.resources.new_transaction
import com.jefisu.portlens.designsystem.generated.resources.shortcut_n
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun TopBar(
    competenceLabel: String,
    onCompetenceClick: () -> Unit,
    onNewTransactionClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .border(1.dp, PortLensTheme.colors.borderSubtle)
            .padding(horizontal = 24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CompetenceChip(
            competenceLabel = competenceLabel,
            onClick = onCompetenceClick,
        )

        NewTransactionButton(onClick = onNewTransactionClick)
    }
}

@Composable
internal fun WebShellHeader(
    competenceLabel: String,
    navItems: List<ShellNavItemUi>,
    onNavClick: (ShellDestination) -> Unit,
    onCompetenceClick: () -> Unit,
    onNewTransactionClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth()
            .background(PortLensTheme.colors.bgBase),
    ) {
        val headerWidth = mainContentWidth(maxWidth - 48.dp)
        val strongDividerColor = PortLensTheme.colors.borderDefault.copy(alpha = 0.85f)
        val softDividerColor = PortLensTheme.colors.borderSubtle.copy(alpha = 0.42f)

        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center,
            ) {
                Row(
                    modifier = Modifier
                        .width(headerWidth)
                        .padding(vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    BrandBlock()
                    HeaderActions(
                        competenceLabel = competenceLabel,
                        onCompetenceClick = onCompetenceClick,
                        onNewTransactionClick = onNewTransactionClick,
                    )
                }
            }

            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 1.dp,
                color = strongDividerColor,
            )

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center,
            ) {
                Row(
                    modifier = Modifier
                        .width(headerWidth),
                    horizontalArrangement = Arrangement.spacedBy(22.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    navItems.forEach { item ->
                        WebNavItem(
                            item = item,
                            onClick = { onNavClick(item.destination) },
                        )
                    }
                }
            }

            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 1.dp,
                color = softDividerColor,
            )
        }
    }
}

@Composable
internal fun BrandBlock() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(26.dp)
                .clip(RoundedCornerShape(7.dp))
                .background(PortLensTheme.colors.accent),
            contentAlignment = Alignment.Center,
        ) {
            Image(
                painter = painterResource(Res.drawable.ic_portlens_mark),
                contentDescription = null,
                modifier = Modifier.size(14.dp),
            )
        }
        Text(
            text = stringResource(Res.string.app_name),
            style = PortLensTheme.typography.displayMd,
            color = PortLensTheme.colors.textPrimary,
        )
    }
}

@Composable
private fun HeaderActions(
    competenceLabel: String,
    onCompetenceClick: () -> Unit,
    onNewTransactionClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CompetenceChip(
            competenceLabel = competenceLabel,
            onClick = onCompetenceClick,
        )
        NewTransactionButton(onClick = onNewTransactionClick)
    }
}

@Composable
private fun CompetenceChip(competenceLabel: String, onClick: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stringResource(Res.string.competence_label),
            style = PortLensTheme.typography.bodySm,
            color = PortLensTheme.colors.textTertiary,
        )
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(7.dp))
                .background(PortLensTheme.colors.bgSurface2)
                .border(1.dp, PortLensTheme.colors.borderDefault, RoundedCornerShape(7.dp))
                .clickable(onClick = onClick)
                .padding(horizontal = 10.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = competenceLabel,
                style = PortLensTheme.typography.numMd,
                color = PortLensTheme.colors.textPrimary,
            )
            Image(
                painter = painterResource(Res.drawable.ic_chevron_down),
                contentDescription = null,
                modifier = Modifier.size(10.dp),
                colorFilter = ColorFilter.tint(PortLensTheme.colors.textTertiary),
            )
        }
    }
}

@Composable
private fun NewTransactionButton(onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(PortLensTheme.colors.accent)
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(7.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "+",
            style = PortLensTheme.typography.headingMd,
            color = PortLensTheme.colors.accentFg,
        )
        Text(
            text = stringResource(Res.string.new_transaction),
            style = PortLensTheme.typography.bodySm,
            color = PortLensTheme.colors.accentFg,
            fontWeight = FontWeight.SemiBold,
        )
        Text(
            text = stringResource(Res.string.shortcut_n),
            modifier = Modifier
                .clip(RoundedCornerShape(3.dp))
                .background(Color(0x26000000))
                .padding(horizontal = 5.dp, vertical = 1.dp),
            style = PortLensTheme.typography.micro,
            color = PortLensTheme.colors.accentFg,
        )
    }
}

@Composable
private fun WebNavItem(item: ShellNavItemUi, onClick: () -> Unit) {
    val indicatorColor = if (item.isSelected) {
        PortLensTheme.colors.textPrimary
    } else {
        Color.Transparent
    }

    Column(
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(top = 14.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        ShellNavItemContent(
            item = item,
            textStyle = PortLensTheme.typography.bodySm,
            itemSpacing = 8.dp,
        )
        HorizontalDivider(
            modifier = Modifier.width(navIndicatorWidth(item.destination.label())),
            thickness = 2.dp,
            color = indicatorColor,
        )
    }
}

@Composable
private fun navIndicatorWidth(label: String): Dp = when {
    label.length >= 14 -> 90.dp
    label.length >= 10 -> 74.dp
    else -> 58.dp
}

@Preview
@Composable
private fun TopBarPreview() {
    PortLensTheme {
        TopBar(
            competenceLabel = "Nov/2025",
            onCompetenceClick = {},
            onNewTransactionClick = {},
        )
    }
}
