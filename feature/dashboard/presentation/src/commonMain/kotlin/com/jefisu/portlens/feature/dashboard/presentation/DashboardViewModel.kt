package com.jefisu.portlens.feature.dashboard.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jefisu.portlens.core.domain.CompetenceMonth
import com.jefisu.portlens.core.domain.GetDashboardSnapshot
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val getDashboardSnapshot: GetDashboardSnapshot,
) : ViewModel() {

    private val _state = MutableStateFlow(DashboardState())
    val state: StateFlow<DashboardState> = _state

    private var selectedCompetence: CompetenceMonth? = null
    private var hasLoadedCompetence = false
    private var loadJob: Job? = null

    fun onAction(action: DashboardAction) {
        when (action) {
            is DashboardAction.OnRetryClick -> loadDashboard()
            else -> Unit
        }
    }

    fun setCompetence(competence: CompetenceMonth) {
        if (selectedCompetence == competence && hasLoadedCompetence) return
        selectedCompetence = competence
        hasLoadedCompetence = true
        loadDashboard()
    }

    fun loadDashboard() {
        val competence = selectedCompetence ?: return

        loadJob?.cancel()
        loadJob = viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            try {
                val snapshot = getDashboardSnapshot(competence)
                _state.value = snapshot.toDashboardState()
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = e.message.orEmpty(),
                    )
                }
            }
        }
    }
}
