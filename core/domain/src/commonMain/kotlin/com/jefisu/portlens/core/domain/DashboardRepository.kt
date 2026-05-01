package com.jefisu.portlens.core.domain

import kotlinx.coroutines.flow.Flow

interface DashboardRepository {
    fun getAvailableCompetences(currentLocalCompetence: CompetenceMonth): Flow<List<CompetenceMonth>>

    fun getPersistedCompetences(): Flow<List<CompetenceMonth>>

    fun getDashboardSnapshot(competence: CompetenceMonth): Flow<DashboardSnapshot>
}
