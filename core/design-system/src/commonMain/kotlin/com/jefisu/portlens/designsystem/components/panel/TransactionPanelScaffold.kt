package com.jefisu.portlens.designsystem.components.panel

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jefisu.portlens.designsystem.PortLensTheme
import com.jefisu.portlens.designsystem.generated.resources.Res
import com.jefisu.portlens.designsystem.generated.resources.close
import com.jefisu.portlens.designsystem.generated.resources.new_transaction
import org.jetbrains.compose.resources.stringResource

@Composable
fun TransactionPanelScaffold(
    onClosePanel: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Surface(
        modifier = modifier
            .width(420.dp)
            .fillMaxHeight(),
        color = PortLensTheme.colors.bgBase,
        shape = RoundedCornerShape(topStart = 0.dp, bottomStart = 0.dp),
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, PortLensTheme.colors.borderSubtle)
                    .padding(horizontal = 22.dp, vertical = 18.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(Res.string.new_transaction),
                    style = PortLensTheme.typography.headingLg,
                    color = PortLensTheme.colors.textPrimary,
                )
                Text(
                    text = stringResource(Res.string.close),
                    style = PortLensTheme.typography.bodySm,
                    color = PortLensTheme.colors.textSecondary,
                    modifier = Modifier.clickable(onClick = onClosePanel),
                )
            }
            Column(
                modifier = Modifier.padding(horizontal = 22.dp, vertical = 20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                content()
            }
        }
    }
}

@Preview
@Composable
private fun TransactionPanelScaffoldPreview() {
    PortLensTheme {
        TransactionPanelScaffold(onClosePanel = {}) {
            Text(
                text = "Placeholder panel",
                color = PortLensTheme.colors.textPrimary,
            )
        }
    }
}
