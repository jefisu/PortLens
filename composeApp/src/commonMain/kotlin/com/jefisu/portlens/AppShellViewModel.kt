package com.jefisu.portlens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jefisu.portlens.core.domain.CompetenceMonth
import com.jefisu.portlens.core.domain.DashboardRepository
import com.jefisu.portlens.core.presentation.stateInViewModel
import com.jefisu.portlens.core.domain.TransactionType
import com.jefisu.portlens.core.domain.emptyDashboardSnapshotFor
import com.jefisu.portlens.designsystem.components.shell.CompetenceIndicatorToneUi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.toLocalDateTime

class AppShellViewModel(
    private val dashboardRepository: DashboardRepository,
) : ViewModel() {

    private var hasLoadedCompetences = false
    private var availableCompetencesJob: Job? = null
    private var selectedSnapshotJob: Job? = null
    private var competenceIndicatorTonesJob: Job? = null

    private val _state =
        MutableStateFlow(AppShellState(selectedCompetence = currentLocalCompetence()))
    val state = _state
        .onStart {
            if (!hasLoadedCompetences) {
                hasLoadedCompetences = true
                loadAvailableCompetences()
            }
        }
        .stateInViewModel(initialValue = _state.value)

    fun onAction(action: AppShellAction) {
        when (action) {
            is AppShellAction.OnCompetenceClick -> {
                _state.update {
                    it.copy(isCompetenceMenuExpanded = !it.isCompetenceMenuExpanded)
                }
            }

            is AppShellAction.OnCompetenceMenuDismiss -> {
                _state.update { it.copy(isCompetenceMenuExpanded = false) }
            }

            is AppShellAction.OnCompetenceSelected -> {
                _state.update {
                    it.copy(
                        selectedCompetence = action.competence,
                        isCompetenceMenuExpanded = false,
                    )
                }

                viewModelScope.launch {
                    updateDashboardData(
                        selected = action.competence,
                        available = _state.value.availableCompetences,
                    )
                }
            }

            is AppShellAction.OnNewTransactionClick -> {
                _state.update { it.copy(isNewTransactionPanelOpen = true) }
            }

            is AppShellAction.OnCloseTransactionPanel -> {
                _state.update { it.copy(isNewTransactionPanelOpen = false) }
            }
        }
    }

    private suspend fun loadAvailableCompetences() {
        val currentLocal = currentLocalCompetence()
        availableCompetencesJob?.cancel()
        availableCompetencesJob = viewModelScope.launch {
            dashboardRepository.getAvailableCompetences(currentLocal).collectLatest { competences ->
                val available = competences.ifEmpty { listOf(currentLocal) }
                val currentSelected = _state.value.selectedCompetence
                val selected = if (available.contains(currentSelected)) {
                    currentSelected
                } else if (available.contains(currentLocal)) {
                    currentLocal
                } else {
                    available.lastOrNull() ?: currentLocal
                }

                _state.update {
                    it.copy(
                        availableCompetences = available,
                        selectedCompetence = selected,
                    )
                }

                updateDashboardData(selected = selected, available = available)
            }
        }
    }

    private fun updateDashboardData(
        selected: CompetenceMonth,
        available: List<CompetenceMonth>,
    ) {
        selectedSnapshotJob?.cancel()
        selectedSnapshotJob = viewModelScope.launch {
            dashboardRepository.getDashboardSnapshot(selected).collectLatest { selectedSnapshot ->
                _state.update {
                    it.copy(snapshot = selectedSnapshot)
                }
            }
        }

        competenceIndicatorTonesJob?.cancel()
        competenceIndicatorTonesJob = viewModelScope.launch {
            dashboardRepository.getPersistedCompetences().collectLatest { persistedCompetences ->
                val persistedSet = persistedCompetences.toSet()
                val resolvedTones = available.associateWith { competence ->
                    if (competence !in persistedSet) {
                        null
                    } else {
                        dashboardRepository.getDashboardSnapshot(competence).first().toCompetenceIndicatorTone()
                    }
                }

                _state.update { state ->
                    state.copy(competenceIndicatorTones = resolvedTones)
                }
            }
        }
    }

    private fun currentLocalCompetence(): CompetenceMonth {
        val now = kotlin.time.Clock.System.now()
        val timeZone = kotlinx.datetime.TimeZone.currentSystemDefault()
        val localDate = now.toLocalDateTime(timeZone).date
        return CompetenceMonth(
            year = localDate.year,
            month = localDate.month,
        )
    }
}

private fun com.jefisu.portlens.core.domain.DashboardSnapshot.toCompetenceIndicatorTone(): CompetenceIndicatorToneUi? {
    val hasPositiveSell = latestTransactions.any { transaction ->
        transaction.type == TransactionType.Sell && (transaction.realizedResult?.cents ?: 0L) > 0
    }

    return when {
        estimatedTaxInMonth.cents > 0 -> CompetenceIndicatorToneUi.Negative
        hasPositiveSell -> CompetenceIndicatorToneUi.Positive
        latestTransactions.isNotEmpty() -> CompetenceIndicatorToneUi.Warning
        else -> null
    }
}
