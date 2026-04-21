package com.jefisu.portlens.designsystem.components.shell

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jefisu.portlens.designsystem.PortLensTheme
import com.jefisu.portlens.designsystem.components.card.MiniExemptionCard
import com.jefisu.portlens.designsystem.components.card.model.MiniExemptionCardUi
import com.jefisu.portlens.designsystem.components.common.model.SemanticTone
import com.jefisu.portlens.designsystem.components.shell.model.ShellDestination
import com.jefisu.portlens.designsystem.components.shell.model.ShellNavItemUi
import com.jefisu.portlens.designsystem.generated.resources.Res
import com.jefisu.portlens.designsystem.generated.resources.sidebar_navigation
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun Sidebar(
    navItems: List<ShellNavItemUi>,
    miniExemptionCard: MiniExemptionCardUi,
    onNavClick: (ShellDestination) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxHeight()
            .background(PortLensTheme.colors.bgBase)
            .border(1.dp, PortLensTheme.colors.borderSubtle)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp),
    ) {
        Row(
            modifier = Modifier.padding(start = 4.dp, end = 4.dp, bottom = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            BrandBlock()
        }

        Text(
            text = stringResource(Res.string.sidebar_navigation),
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
            style = PortLensTheme.typography.micro,
            color = PortLensTheme.colors.textTertiary,
        )

        navItems.forEach { item ->
            SidebarItem(
                item = item,
                onClick = { onNavClick(item.destination) },
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        MiniExemptionCard(card = miniExemptionCard)
    }
}

@Composable
private fun SidebarItem(item: ShellNavItemUi, onClick: () -> Unit) {
    val labelStyle = PortLensTheme.typography.bodySm.copy(lineHeight = 13.sp)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(if (item.isSelected) PortLensTheme.colors.bgSurface2 else Color.Transparent)
            .clickable(onClick = onClick)
            .padding(horizontal = 10.dp, vertical = 9.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ShellNavItemContent(
            item = item,
            textStyle = labelStyle,
            itemSpacing = 10.dp,
        )
    }
}

@Composable
internal fun ShellNavItemContent(
    item: ShellNavItemUi,
    textStyle: TextStyle,
    itemSpacing: Dp,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(itemSpacing),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier.size(16.dp),
            contentAlignment = Alignment.Center,
        ) {
            Image(
                painter = painterResource(item.destination.icon()),
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                colorFilter = ColorFilter.tint(navItemColor(item.isSelected)),
            )
        }
        Text(
            text = item.destination.label(),
            style = textStyle,
            color = navItemColor(item.isSelected),
            fontWeight = if (item.isSelected) FontWeight.Medium else FontWeight.Normal,
        )
    }
}

@Preview
@Composable
private fun SidebarPreview() {
    PortLensTheme {
        Sidebar(
            navItems = listOf(
                ShellNavItemUi(ShellDestination.Overview, true),
                ShellNavItemUi(ShellDestination.Transactions, false),
            ),
            miniExemptionCard = MiniExemptionCardUi(
                monthLabel = "Nov",
                soldAmountLabel = "R$ 12.340,00",
                usedRatio = 0.617f,
                usedRatioLabel = "61,7%",
                remainingLabel = "R$ 7.660,00",
                tone = SemanticTone.Positive,
            ),
            onNavClick = {},
        )
    }
}
