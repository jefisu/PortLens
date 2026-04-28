package com.jefisu.portlens

import androidx.lifecycle.ViewModel
import com.jefisu.portlens.core.domain.CompetenceMonth
import com.jefisu.portlens.core.domain.GetAvailableCompetences
import com.jefisu.portlens.core.presentation.stateInViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.datetime.toLocalDateTime

class AppShellViewModel(private val getAvailableCompetences: GetAvailableCompetences) :
    ViewModel() {

    private var hasLoadedCompetences = false

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
        val competences = getAvailableCompetences(currentLocal)

        val available = competences.ifEmpty { listOf(currentLocal) }

        val selected = if (available.contains(currentLocal)) {
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
