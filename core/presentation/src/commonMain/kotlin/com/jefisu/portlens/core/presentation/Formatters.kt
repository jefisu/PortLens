package com.jefisu.portlens.core.presentation

import androidx.compose.runtime.Composable
import com.jefisu.portlens.core.domain.CompetenceMonth
import com.jefisu.portlens.core.domain.Money
import com.jefisu.portlens.core.domain.Timestamp
import com.jefisu.portlens.core.domain.TransactionDate
import com.jefisu.portlens.core.presentation.generated.resources.Res
import com.jefisu.portlens.core.presentation.generated.resources.month_short_april
import com.jefisu.portlens.core.presentation.generated.resources.month_short_august
import com.jefisu.portlens.core.presentation.generated.resources.month_short_december
import com.jefisu.portlens.core.presentation.generated.resources.month_short_february
import com.jefisu.portlens.core.presentation.generated.resources.month_short_january
import com.jefisu.portlens.core.presentation.generated.resources.month_short_july
import com.jefisu.portlens.core.presentation.generated.resources.month_short_june
import com.jefisu.portlens.core.presentation.generated.resources.month_short_march
import com.jefisu.portlens.core.presentation.generated.resources.month_short_may
import com.jefisu.portlens.core.presentation.generated.resources.month_short_november
import com.jefisu.portlens.core.presentation.generated.resources.month_short_october
import com.jefisu.portlens.core.presentation.generated.resources.month_short_september
import kotlin.math.abs
import kotlinx.datetime.Month
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

fun Money.formatBrl(): String {
    val absoluteCents = abs(cents)
    val integerPart = absoluteCents / 100
    val decimalPart = absoluteCents % 100
    val groupedInteger = integerPart
        .toString()
        .reversed()
        .chunked(3)
        .joinToString(".")
        .reversed()
    val prefix = if (cents < 0) "-" else ""

    return $$"$${prefix}R$ $$groupedInteger,$${decimalPart.toString().padStart(2, '0')}"
}

fun Money.formatSignedBrl(): String {
    val absolute = Money(cents = abs(cents)).formatBrl()
    return when {
        isPositive() -> "+$absolute"
        isNegative() -> "−$absolute"
        else -> absolute
    }
}

fun Month.shortMonthLabelResource(): StringResource = when (this) {
    Month.JANUARY -> Res.string.month_short_january
    Month.FEBRUARY -> Res.string.month_short_february
    Month.MARCH -> Res.string.month_short_march
    Month.APRIL -> Res.string.month_short_april
    Month.MAY -> Res.string.month_short_may
    Month.JUNE -> Res.string.month_short_june
    Month.JULY -> Res.string.month_short_july
    Month.AUGUST -> Res.string.month_short_august
    Month.SEPTEMBER -> Res.string.month_short_september
    Month.OCTOBER -> Res.string.month_short_october
    Month.NOVEMBER -> Res.string.month_short_november
    Month.DECEMBER -> Res.string.month_short_december
}

@Composable
fun Month.shortMonthLabel(): String = stringResource(shortMonthLabelResource())
    .replaceFirstChar { it.uppercase() }

@Composable
fun CompetenceMonth.formatCompetence(): String =
    "${stringResource(month.shortMonthLabelResource())}/$year"

fun TransactionDate.formatShortDate(): String {
    val formattedDay = day.toString().padStart(2, '0')
    val formattedMonth = month.toString().padStart(2, '0')
    return "$formattedDay/$formattedMonth/$year"
}

fun Timestamp.formatUpdatedAt(): String {
    val formattedDay = day.toString().padStart(2, '0')
    val formattedMonth = month.toString().padStart(2, '0')
    val formattedHour = hour.toString().padStart(2, '0')
    val formattedMinute = minute.toString().padStart(2, '0')
    return "$formattedDay/$formattedMonth · $formattedHour:$formattedMinute"
}
