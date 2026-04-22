package com.jefisu.portlens

import com.jefisu.portlens.core.domain.CompetenceMonth

data class AppShellState(
    val selectedCompetence: CompetenceMonth,
    val availableCompetences: List<CompetenceMonth> = emptyList(),
    val isCompetenceMenuExpanded: Boolean = false,
    val isNewTransactionPanelOpen: Boolean = false,
)
