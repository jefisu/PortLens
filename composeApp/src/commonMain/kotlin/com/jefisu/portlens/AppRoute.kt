package com.jefisu.portlens

import kotlinx.serialization.Serializable

@Serializable
sealed interface AppRoute {
    @Serializable
    data object Dashboard : AppRoute

    @Serializable
    data object Transactions : AppRoute

    @Serializable
    data object CurrentPortfolio : AppRoute

    @Serializable
    data object MonthlySummary : AppRoute
}
