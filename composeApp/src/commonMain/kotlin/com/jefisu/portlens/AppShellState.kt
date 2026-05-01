package com.jefisu.portlens

import com.jefisu.portlens.core.domain.CompetenceMonth
import com.jefisu.portlens.core.domain.DashboardSnapshot
import com.jefisu.portlens.core.domain.emptyDashboardSnapshotFor
import com.jefisu.portlens.designsystem.components.shell.CompetenceIndicatorToneUi

data class AppShellState(
    val selectedCompetence: CompetenceMonth,
    val snapshot: DashboardSnapshot = emptyDashboardSnapshotFor(selectedCompetence),
    val availableCompetences: List<CompetenceMonth> = emptyList(),
    val competenceIndicatorTones: Map<CompetenceMonth, CompetenceIndicatorToneUi?> = emptyMap(),
    val isCompetenceMenuExpanded: Boolean = false,
    val isNewTransactionPanelOpen: Boolean = false,
)
