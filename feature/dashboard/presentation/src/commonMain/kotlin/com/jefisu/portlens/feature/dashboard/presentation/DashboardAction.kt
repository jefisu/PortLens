package com.jefisu.portlens.feature.dashboard.presentation

sealed interface DashboardAction {
    data object OnViewAllTransactionsClick : DashboardAction
    data object OnRetryClick : DashboardAction
}
