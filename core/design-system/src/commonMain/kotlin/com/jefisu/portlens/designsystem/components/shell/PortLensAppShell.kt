package com.jefisu.portlens.designsystem.components.shell

import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jefisu.portlens.core.presentation.isWebPlatform
import com.jefisu.portlens.designsystem.PortLensTheme
import com.jefisu.portlens.designsystem.components.card.model.MiniExemptionCardUi
import com.jefisu.portlens.designsystem.components.common.model.SemanticTone
import com.jefisu.portlens.designsystem.components.panel.TransactionPanelScaffold
import com.jefisu.portlens.designsystem.components.shell.model.ShellDestination
import com.jefisu.portlens.designsystem.components.shell.model.ShellNavItemUi

@Composable
fun PortLensAppShell(
    competenceLabel: String,
    competenceOptions: List<CompetenceOptionUi>,
    isCompetenceMenuExpanded: Boolean,
    navItems: List<ShellNavItemUi>,
    miniExemptionCard: MiniExemptionCardUi,
    isTransactionPanelOpen: Boolean,
    onNavClick: (ShellDestination) -> Unit,
    onCompetenceClick: () -> Unit,
    onCompetenceMenuDismiss: () -> Unit,
    onNewTransactionClick: () -> Unit,
    onClosePanel: () -> Unit,
    onOpenPanel: () -> Unit,
    panelContent: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val shortcutFocusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        shortcutFocusRequester.requestFocus()
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(PortLensTheme.colors.bgBase)
            .focusRequester(shortcutFocusRequester)
            .focusable()
            .onPreviewKeyEvent { event ->
                if (event.type != KeyEventType.KeyUp) return@onPreviewKeyEvent false
                when (event.key) {
                    Key.N -> {
                        if (!isTransactionPanelOpen) {
                            onOpenPanel()
                        }
                        true
                    }

                    Key.Escape -> {
                        if (isTransactionPanelOpen) {
                            onClosePanel()
                        }
                        true
                    }

                    else -> false
                }
            },
    ) {
        val useWebTopNavigation = isWebPlatform()

        if (useWebTopNavigation) {
            Column(modifier = Modifier.fillMaxSize()) {
                WebShellHeader(
                    competenceLabel = competenceLabel,
                    competenceOptions = competenceOptions,
                    isCompetenceMenuExpanded = isCompetenceMenuExpanded,
                    navItems = navItems,
                    onNavClick = onNavClick,
                    onCompetenceClick = onCompetenceClick,
                    onCompetenceMenuDismiss = onCompetenceMenuDismiss,
                    onNewTransactionClick = onNewTransactionClick,
                )

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(top = 20.dp, bottom = 24.dp),
                ) {
                    WebMainContentContainer {
                        content()
                    }

                    if (isTransactionPanelOpen) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color(0x990A0B0D)),
                        )
                    }
                }
            }
        } else {
            BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
                val useCompactSidebar = maxWidth < 1200.dp
                val sidebarWidth = if (useCompactSidebar) 224.dp else 252.dp

                Row(modifier = Modifier.fillMaxSize()) {
                    Sidebar(
                        navItems = navItems,
                        miniExemptionCard = miniExemptionCard,
                        onNavClick = onNavClick,
                        modifier = Modifier.width(sidebarWidth),
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1f),
                    ) {
                        TopBar(
                            competenceLabel = competenceLabel,
                            competenceOptions = competenceOptions,
                            isCompetenceMenuExpanded = isCompetenceMenuExpanded,
                            onCompetenceClick = onCompetenceClick,
                            onCompetenceMenuDismiss = onCompetenceMenuDismiss,
                            onNewTransactionClick = onNewTransactionClick,
                        )

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth(),
                        ) {
                            DesktopContentContainer {
                                content()
                            }

                            if (isTransactionPanelOpen) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(Color(0x990A0B0D)),
                                )
                            }
                        }
                    }
                }
            }
        }

        if (isTransactionPanelOpen) {
            Box(modifier = Modifier.align(Alignment.TopEnd)) {
                TransactionPanelScaffold(
                    onClosePanel = onClosePanel,
                    content = panelContent,
                )
            }
        }
    }
}

@Composable
private fun WebMainContentContainer(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
    ) {
        val contentWidth = mainContentWidth(maxWidth)

        Box(
            modifier = Modifier
                .width(contentWidth)
                .align(Alignment.TopCenter),
        ) {
            content()
        }
    }
}

@Composable
private fun DesktopContentContainer(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(
                start = 24.dp,
                end = 24.dp,
                top = 12.dp,
                bottom = 16.dp,
            ),
        contentAlignment = Alignment.TopCenter,
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            content()
        }
    }
}

internal val PortLensMainContentMaxWidth = 1156.dp

internal fun mainContentWidth(availableWidth: Dp): Dp =
    minOf(availableWidth, PortLensMainContentMaxWidth)

@Preview
@Composable
private fun PortLensAppShellPreview() {
    PortLensTheme {
        PortLensAppShell(
            competenceLabel = "Nov/2025",
            competenceOptions = listOf(
                CompetenceOptionUi(label = "Out/2025", isSelected = false, onClick = {}),
                CompetenceOptionUi(label = "Nov/2025", isSelected = true, onClick = {}),
            ),
            isCompetenceMenuExpanded = true,
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
            isTransactionPanelOpen = true,
            onNavClick = {},
            onCompetenceClick = {},
            onCompetenceMenuDismiss = {},
            onNewTransactionClick = {},
            onClosePanel = {},
            onOpenPanel = {},
            panelContent = {},
        ) {
            Box(modifier = Modifier.fillMaxSize().background(PortLensTheme.colors.bgSurface))
        }
    }
}
