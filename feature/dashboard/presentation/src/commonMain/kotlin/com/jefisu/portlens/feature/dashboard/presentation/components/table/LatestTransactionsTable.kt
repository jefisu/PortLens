package com.jefisu.portlens.feature.dashboard.presentation.components.table

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jefisu.portlens.core.presentation.isWebPlatform
import com.jefisu.portlens.core.presentation.shortMonthLabel
import com.jefisu.portlens.designsystem.PortLensDimens
import com.jefisu.portlens.designsystem.PortLensTheme
import com.jefisu.portlens.designsystem.components.badge.PortLensBadge
import com.jefisu.portlens.feature.dashboard.presentation.DashboardTransactionTypeUi
import com.jefisu.portlens.feature.dashboard.presentation.LatestTransactionUi
import com.jefisu.portlens.feature.dashboard.presentation.components.common.label
import com.jefisu.portlens.feature.dashboard.presentation.components.common.resultColor
import com.jefisu.portlens.feature.dashboard.presentation.generated.resources.Res
import com.jefisu.portlens.feature.dashboard.presentation.generated.resources.dashboard_column_date
import com.jefisu.portlens.feature.dashboard.presentation.generated.resources.dashboard_column_month_volume
import com.jefisu.portlens.feature.dashboard.presentation.generated.resources.dashboard_column_price
import com.jefisu.portlens.feature.dashboard.presentation.generated.resources.dashboard_column_quantity
import com.jefisu.portlens.feature.dashboard.presentation.generated.resources.dashboard_column_result
import com.jefisu.portlens.feature.dashboard.presentation.generated.resources.dashboard_column_ticker
import com.jefisu.portlens.feature.dashboard.presentation.generated.resources.dashboard_column_type
import com.jefisu.portlens.feature.dashboard.presentation.generated.resources.dashboard_column_volume
import com.jefisu.portlens.feature.dashboard.presentation.generated.resources.dashboard_latest_transactions_subtitle
import com.jefisu.portlens.feature.dashboard.presentation.generated.resources.dashboard_latest_transactions_title
import com.jefisu.portlens.feature.dashboard.presentation.generated.resources.dashboard_type_buy
import com.jefisu.portlens.feature.dashboard.presentation.generated.resources.dashboard_type_rebuy
import com.jefisu.portlens.feature.dashboard.presentation.generated.resources.dashboard_type_sell
import com.jefisu.portlens.feature.dashboard.presentation.generated.resources.dashboard_view_all
import kotlinx.datetime.Month
import org.jetbrains.compose.resources.stringResource

@Composable
fun LatestTransactionsTable(
    transactions: List<LatestTransactionUi>,
    month: Month,
    modifier: Modifier = Modifier,
    onViewAllClick: () -> Unit = {},
) {
    BoxWithConstraints(modifier = modifier.fillMaxWidth()) {
        val monthLabel = month.shortMonthLabel()
        val visibleTransactions = transactions.take(DASHBOARD_LATEST_TRANSACTIONS_PREVIEW_COUNT)
        val useDenseDesktopLayout = !isWebPlatform() && maxHeight < 380.dp
        val distributeExtraHeight = !isWebPlatform()

        Surface(
            modifier = Modifier.fillMaxSize(),
            shape = RoundedCornerShape(PortLensDimens.cardCornerRadius),
            color = PortLensTheme.colors.bgSurface,
        ) {
            Column(modifier = Modifier.fillMaxHeight()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 16.dp,
                            vertical = if (useDenseDesktopLayout) 12.dp else 14.dp,
                        ),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                        Text(
                            text = stringResource(Res.string.dashboard_latest_transactions_title),
                            style = PortLensTheme.typography.headingMd,
                            color = PortLensTheme.colors.textPrimary,
                            fontWeight = FontWeight.SemiBold,
                        )
                        Text(
                            text = stringResource(
                                Res.string.dashboard_latest_transactions_subtitle,
                                monthLabel,
                                transactions.size.toString(),
                            ),
                            style = PortLensTheme.typography.micro,
                            color = PortLensTheme.colors.textTertiary,
                        )
                    }
                    Text(
                        text = stringResource(Res.string.dashboard_view_all),
                        modifier = Modifier.clickable(onClick = onViewAllClick),
                        style = PortLensTheme.typography.bodySm,
                        color = PortLensTheme.colors.neutral,
                        textDecoration = TextDecoration.Underline,
                    )
                }

                HorizontalDivider(color = PortLensTheme.colors.borderDefault)

                LatestTransactionsTableContent(
                    transactions = visibleTransactions,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    useDenseDesktopLayout = useDenseDesktopLayout,
                    distributeExtraHeight = distributeExtraHeight,
                )
            }
        }
    }
}

@Composable
private fun LatestTransactionsTableContent(
    transactions: List<LatestTransactionUi>,
    useDenseDesktopLayout: Boolean,
    distributeExtraHeight: Boolean,
    modifier: Modifier = Modifier,
) {
    BoxWithConstraints(modifier = modifier) {
        val useCompactRows = maxWidth < 760.dp
        val density = LocalDensity.current
        val textMeasurer = rememberTextMeasurer()
        val headerLabels = dashboardHeaderLabels()
        val typeLabels = dashboardTypeLabels()
        val tableTextStyles = DashboardTableTextStyles(
            header = PortLensTheme.typography.micro,
            badge = PortLensTheme.typography.micro,
            body = PortLensTheme.typography.numMd,
        )
        val columnWidths = remember(
            transactions,
            density,
            textMeasurer,
            headerLabels,
            typeLabels,
            tableTextStyles,
        ) {
            calculateDashboardTableColumnWidths(
                transactions = transactions,
                headerLabels = headerLabels,
                typeLabels = typeLabels,
                textStyles = tableTextStyles,
                textMeasurer = textMeasurer,
                density = density,
            )
        }
        val dynamicColumnSpacing = remember(maxWidth, columnWidths) {
            calculateDashboardTableColumnSpacing(
                availableWidth = maxWidth,
                columnWidths = columnWidths,
            )
        }
        val tableWidth = remember(columnWidths, dynamicColumnSpacing) {
            columnWidths.fold(0.dp) { acc, width -> acc + width } +
                DashboardTableHorizontalPadding * 2 +
                dynamicColumnSpacing * DashboardTableColumnKeys.lastIndex
        }

        if (useCompactRows) {
            Column {
                transactions.forEachIndexed { index, transaction ->
                    CompactTransactionRow(transaction = transaction)
                    if (index != transactions.lastIndex) {
                        HorizontalDivider(color = PortLensTheme.colors.borderSubtle)
                    }
                }
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .horizontalScroll(rememberScrollState()),
            ) {
                Column(
                    modifier = Modifier
                        .width(tableWidth)
                        .fillMaxHeight(),
                ) {
                    DashboardTableHeader(
                        columnWidths = columnWidths,
                        columnSpacing = dynamicColumnSpacing,
                        dense = useDenseDesktopLayout,
                    )

                    HorizontalDivider(color = PortLensTheme.colors.borderDefault)

                    if (distributeExtraHeight) {
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.SpaceEvenly,
                        ) {
                            transactions.forEachIndexed { index, transaction ->
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .weight(1f),
                                    contentAlignment = Alignment.CenterStart,
                                ) {
                                    DashboardTransactionRow(
                                        transaction = transaction,
                                        columnWidths = columnWidths,
                                        columnSpacing = dynamicColumnSpacing,
                                        dense = useDenseDesktopLayout,
                                        modifier = Modifier.fillMaxWidth(),
                                    )
                                }
                                if (index != transactions.lastIndex) {
                                    HorizontalDivider(color = PortLensTheme.colors.borderSubtle)
                                }
                            }
                        }
                    } else {
                        transactions.forEachIndexed { index, transaction ->
                            DashboardTransactionRow(
                                transaction = transaction,
                                columnWidths = columnWidths,
                                columnSpacing = dynamicColumnSpacing,
                                dense = useDenseDesktopLayout,
                                modifier = Modifier.fillMaxWidth(),
                            )
                            if (index != transactions.lastIndex) {
                                HorizontalDivider(color = PortLensTheme.colors.borderSubtle)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CompactTransactionRow(transaction: LatestTransactionUi) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(
                    text = transaction.ticker,
                    style = PortLensTheme.typography.numMd,
                    color = PortLensTheme.colors.textPrimary,
                    fontWeight = FontWeight.Medium,
                )
                Text(
                    text = transaction.dateLabel,
                    style = PortLensTheme.typography.micro,
                    color = PortLensTheme.colors.textTertiary,
                )
            }

            TypeBadge(transaction = transaction)
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(PortLensDimens.sectionSpacing),
        ) {
            CompactMetric(
                label = stringResource(Res.string.dashboard_column_volume),
                value = transaction.volumeLabel,
                modifier = Modifier.weight(1f),
            )
            CompactMetric(
                label = stringResource(Res.string.dashboard_column_result),
                value = transaction.resultLabel ?: "—",
                valueColor = if (transaction.resultLabel == null) {
                    PortLensTheme.colors.textDisabled
                } else {
                    transaction.type.resultColor()
                },
                modifier = Modifier.weight(1f),
            )
            CompactMetric(
                label = stringResource(Res.string.dashboard_column_month_volume),
                value = transaction.accumulatedMonthlyVolumeLabel ?: "—",
                modifier = Modifier.weight(1f),
            )
        }
    }
}

@Composable
private fun CompactMetric(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
    valueColor: androidx.compose.ui.graphics.Color = PortLensTheme.colors.textPrimary,
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(3.dp)) {
        Text(
            text = label.uppercase(),
            style = PortLensTheme.typography.micro,
            color = PortLensTheme.colors.textTertiary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        Text(
            text = value,
            style = PortLensTheme.typography.numMd,
            color = valueColor,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Composable
private fun dashboardHeaderLabels(): Map<DashboardTableColumnKey, String> = mapOf(
    DashboardTableColumnKey.Date to stringResource(Res.string.dashboard_column_date).uppercase(),
    DashboardTableColumnKey.Ticker to stringResource(
        Res.string.dashboard_column_ticker,
    ).uppercase(),
    DashboardTableColumnKey.Type to stringResource(Res.string.dashboard_column_type).uppercase(),
    DashboardTableColumnKey.Quantity to
        stringResource(Res.string.dashboard_column_quantity).uppercase(),
    DashboardTableColumnKey.Price to stringResource(Res.string.dashboard_column_price).uppercase(),
    DashboardTableColumnKey.Volume to stringResource(
        Res.string.dashboard_column_volume,
    ).uppercase(),
    DashboardTableColumnKey.Result to stringResource(
        Res.string.dashboard_column_result,
    ).uppercase(),
    DashboardTableColumnKey.MonthVolume to
        stringResource(Res.string.dashboard_column_month_volume).uppercase(),
)

@Composable
private fun dashboardTypeLabels(): Map<DashboardTransactionTypeUi, String> = mapOf(
    DashboardTransactionTypeUi.Buy to stringResource(Res.string.dashboard_type_buy),
    DashboardTransactionTypeUi.Sell to stringResource(Res.string.dashboard_type_sell),
    DashboardTransactionTypeUi.Rebuy to stringResource(Res.string.dashboard_type_rebuy),
)

@Composable
private fun DashboardTableHeader(columnWidths: List<Dp>, columnSpacing: Dp, dense: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(PortLensTheme.colors.bgSurface)
            .padding(
                horizontal = DashboardTableHorizontalPadding,
                vertical = if (dense) 8.dp else 10.dp,
            ),
        horizontalArrangement = Arrangement.spacedBy(columnSpacing),
    ) {
        DashboardTableColumnKeys.forEachIndexed { index, column ->
            HeaderCell(
                text = dashboardHeaderLabels().getValue(column),
                width = columnWidths[index],
                align = column.textAlign,
            )
        }
    }
}

@Composable
private fun RowScope.HeaderCell(text: String, width: Dp, align: TextAlign = TextAlign.Start) {
    Text(
        text = text,
        modifier = Modifier.width(width),
        style = PortLensTheme.typography.micro,
        color = PortLensTheme.colors.textTertiary,
        textAlign = align,
        maxLines = 1,
        softWrap = false,
        overflow = TextOverflow.Ellipsis,
    )
}

@Composable
private fun DashboardTransactionRow(
    transaction: LatestTransactionUi,
    columnWidths: List<Dp>,
    columnSpacing: Dp,
    dense: Boolean,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = DashboardTableHorizontalPadding,
                vertical = if (dense) 9.dp else 12.dp,
            ),
        horizontalArrangement = Arrangement.spacedBy(columnSpacing),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        BodyMonoCell(
            text = transaction.dateLabel,
            width = columnWidths[DashboardTableColumnKey.Date.index],
            color = PortLensTheme.colors.textSecondary,
        )
        BodyMonoCell(
            transaction.ticker,
            columnWidths[DashboardTableColumnKey.Ticker.index],
            PortLensTheme.colors.textPrimary,
            FontWeight.Medium,
        )
        TypeBadge(
            transaction = transaction,
            modifier = Modifier.width(columnWidths[DashboardTableColumnKey.Type.index]),
        )
        BodyMonoCell(
            transaction.quantityLabel,
            columnWidths[DashboardTableColumnKey.Quantity.index],
            PortLensTheme.colors.textPrimary,
            textAlign = TextAlign.End,
        )
        BodyMonoCell(
            transaction.unitPriceLabel,
            columnWidths[DashboardTableColumnKey.Price.index],
            PortLensTheme.colors.textPrimary,
            textAlign = TextAlign.End,
        )
        BodyMonoCell(
            transaction.volumeLabel,
            columnWidths[DashboardTableColumnKey.Volume.index],
            PortLensTheme.colors.textPrimary,
            textAlign = TextAlign.End,
        )
        BodyMonoCell(
            transaction.resultLabel ?: "—",
            columnWidths[DashboardTableColumnKey.Result.index],
            if (transaction.resultLabel == null) {
                PortLensTheme.colors.textDisabled
            } else {
                transaction.type.resultColor()
            },
            textAlign = TextAlign.End,
        )
        BodyMonoCell(
            transaction.accumulatedMonthlyVolumeLabel ?: "—",
            columnWidths[DashboardTableColumnKey.MonthVolume.index],
            if (transaction.accumulatedMonthlyVolumeLabel == null) {
                PortLensTheme.colors.textTertiary
            } else {
                PortLensTheme.colors.textPrimary
            },
            textAlign = TextAlign.End,
            fontWeight = if (transaction.ticker == "VALE3") {
                FontWeight.SemiBold
            } else {
                FontWeight.Medium
            },
        )
    }
}

@Composable
private fun TypeBadge(transaction: LatestTransactionUi, modifier: Modifier = Modifier) {
    val background = when (transaction.type) {
        DashboardTransactionTypeUi.Buy -> PortLensTheme.colors.neutralSoft
        DashboardTransactionTypeUi.Sell -> PortLensTheme.colors.warningSoft
        DashboardTransactionTypeUi.Rebuy -> PortLensTheme.colors.accent.copy(alpha = 0.13f)
    }
    val color = when (transaction.type) {
        DashboardTransactionTypeUi.Buy -> PortLensTheme.colors.neutral
        DashboardTransactionTypeUi.Sell -> PortLensTheme.colors.warning
        DashboardTransactionTypeUi.Rebuy -> PortLensTheme.colors.accent
    }

    Box(modifier = modifier) {
        PortLensBadge(
            text = transaction.type.label(),
            containerColor = background,
            contentColor = color,
        )
    }
}

@Composable
private fun RowScope.BodyMonoCell(
    text: String,
    width: Dp,
    color: androidx.compose.ui.graphics.Color,
    fontWeight: FontWeight = FontWeight.Normal,
    textAlign: TextAlign = TextAlign.Start,
) {
    Text(
        text = text,
        modifier = Modifier.width(width),
        style = PortLensTheme.typography.numMd,
        color = color,
        fontWeight = fontWeight,
        textAlign = textAlign,
        maxLines = 1,
        softWrap = false,
        overflow = TextOverflow.Ellipsis,
    )
}

private enum class DashboardTableColumnKey(val index: Int) {
    Date(index = 0),
    Ticker(index = 1),
    Type(index = 2),
    Quantity(index = 3),
    Price(index = 4),
    Volume(index = 5),
    Result(index = 6),
    MonthVolume(index = 7),
}

private val DashboardTableColumnKey.textAlign: TextAlign
    get() = when (this) {
        DashboardTableColumnKey.Date,
        DashboardTableColumnKey.Ticker,
        DashboardTableColumnKey.Type,
        -> TextAlign.Start

        DashboardTableColumnKey.Quantity,
        DashboardTableColumnKey.Price,
        DashboardTableColumnKey.Volume,
        DashboardTableColumnKey.Result,
        DashboardTableColumnKey.MonthVolume,
        -> TextAlign.End
    }

private val DashboardTableColumnKeys = DashboardTableColumnKey.entries
private val DashboardTableHorizontalPadding = 14.dp
private val DashboardTableMinColumnSpacing = 6.dp
private val DashboardTypeBadgeHorizontalPadding = 16.dp
private val DashboardTableColumnSafetyPadding = 8.dp
private const val DASHBOARD_LATEST_TRANSACTIONS_PREVIEW_COUNT = 3

private data class DashboardTableTextStyles(
    val header: TextStyle,
    val badge: TextStyle,
    val body: TextStyle,
)

private fun calculateDashboardTableColumnWidths(
    transactions: List<LatestTransactionUi>,
    headerLabels: Map<DashboardTableColumnKey, String>,
    typeLabels: Map<DashboardTransactionTypeUi, String>,
    textStyles: DashboardTableTextStyles,
    textMeasurer: TextMeasurer,
    density: Density,
): List<Dp> = DashboardTableColumnKeys.map { column ->
    val headerWidth = measureTextWidth(
        text = headerLabels.getValue(column),
        style = textStyles.header,
        textMeasurer = textMeasurer,
        density = density,
    )
    val bodyWidth = transactions.maxOfOrNull { transaction ->
        measureColumnContentWidth(
            column = column,
            transaction = transaction,
            typeLabels = typeLabels,
            textStyles = textStyles,
            textMeasurer = textMeasurer,
            density = density,
        )
    } ?: 0.dp

    maxOf(headerWidth, bodyWidth) + DashboardTableColumnSafetyPadding
}

private fun calculateDashboardTableColumnSpacing(availableWidth: Dp, columnWidths: List<Dp>): Dp {
    val gaps = DashboardTableColumnKeys.lastIndex
    val contentWidth = columnWidths.fold(0.dp) { acc, width -> acc + width }
    val usableWidth = (availableWidth - DashboardTableHorizontalPadding * 2).coerceAtLeast(0.dp)
    val remainingWidth = usableWidth - contentWidth
    val distributedSpacing = if (gaps > 0) remainingWidth / gaps else 0.dp

    return if (remainingWidth > 0.dp) {
        maxOf(DashboardTableMinColumnSpacing, distributedSpacing)
    } else {
        DashboardTableMinColumnSpacing
    }
}

private fun measureColumnContentWidth(
    column: DashboardTableColumnKey,
    transaction: LatestTransactionUi,
    typeLabels: Map<DashboardTransactionTypeUi, String>,
    textStyles: DashboardTableTextStyles,
    textMeasurer: TextMeasurer,
    density: Density,
): Dp = when (column) {
    DashboardTableColumnKey.Date -> measureTextWidth(
        text = transaction.dateLabel,
        style = textStyles.body,
        textMeasurer = textMeasurer,
        density = density,
    )

    DashboardTableColumnKey.Ticker -> measureTextWidth(
        text = transaction.ticker,
        style = textStyles.body,
        fontWeight = FontWeight.Medium,
        textMeasurer = textMeasurer,
        density = density,
    )

    DashboardTableColumnKey.Type -> measureTextWidth(
        text = typeLabels.getValue(transaction.type),
        style = textStyles.badge,
        textMeasurer = textMeasurer,
        density = density,
    ) + DashboardTypeBadgeHorizontalPadding

    DashboardTableColumnKey.Quantity -> measureTextWidth(
        text = transaction.quantityLabel,
        style = textStyles.body,
        textMeasurer = textMeasurer,
        density = density,
    )

    DashboardTableColumnKey.Price -> measureTextWidth(
        text = transaction.unitPriceLabel,
        style = textStyles.body,
        textMeasurer = textMeasurer,
        density = density,
    )

    DashboardTableColumnKey.Volume -> measureTextWidth(
        text = transaction.volumeLabel,
        style = textStyles.body,
        textMeasurer = textMeasurer,
        density = density,
    )

    DashboardTableColumnKey.Result -> measureTextWidth(
        text = transaction.resultLabel ?: "—",
        style = textStyles.body,
        textMeasurer = textMeasurer,
        density = density,
    )

    DashboardTableColumnKey.MonthVolume -> measureTextWidth(
        text = transaction.accumulatedMonthlyVolumeLabel ?: "—",
        style = textStyles.body,
        fontWeight = if (transaction.ticker == "VALE3") FontWeight.SemiBold else FontWeight.Medium,
        textMeasurer = textMeasurer,
        density = density,
    )
}

private fun measureTextWidth(
    text: String,
    style: TextStyle,
    textMeasurer: TextMeasurer,
    density: Density,
    fontWeight: FontWeight? = null,
): Dp {
    val textWidthPx = textMeasurer.measure(
        text = text,
        style = style.copy(fontWeight = fontWeight ?: style.fontWeight),
        maxLines = 1,
        softWrap = false,
    ).size.width

    return with(density) { textWidthPx.toDp() }
}
