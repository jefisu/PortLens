package com.jefisu.portlens.core.database.repository

import com.jefisu.portlens.core.database.PortLensDatabase
import com.jefisu.portlens.core.database.dao.DashboardSnapshotDao
import com.jefisu.portlens.core.database.mapper.toCompetenceMonth
import com.jefisu.portlens.core.database.mapper.toDomain
import com.jefisu.portlens.core.domain.CompetenceMonth
import com.jefisu.portlens.core.domain.DashboardRepository
import com.jefisu.portlens.core.domain.DashboardSnapshot
import com.jefisu.portlens.core.domain.competenceRange
import com.jefisu.portlens.core.domain.currentTimestamp
import com.jefisu.portlens.core.domain.emptyDashboardSnapshotFor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class RoomDashboardRepository(
    database: PortLensDatabase,
) : DashboardRepository {
    private val dashboardSnapshotDao: DashboardSnapshotDao = database.dashboardSnapshotDao()

    override fun getAvailableCompetences(
        currentLocalCompetence: CompetenceMonth,
    ): Flow<List<CompetenceMonth>> = flow {
        emitAll(
            dashboardSnapshotDao.observeAvailableCompetences().map { competences ->
                val oldestCompetence = competences
                    .firstOrNull()
                    ?.toCompetenceMonth()
                    ?: currentLocalCompetence

                if (oldestCompetence.absoluteMonthIndex() <= currentLocalCompetence.absoluteMonthIndex()) {
                    competenceRange(start = oldestCompetence, end = currentLocalCompetence)
                } else {
                    listOf(currentLocalCompetence)
                }
            },
        )
    }

    override fun getPersistedCompetences(): Flow<List<CompetenceMonth>> = flow {
        emitAll(
            dashboardSnapshotDao.observeAvailableCompetences().map { competences ->
                competences.map { it.toCompetenceMonth() }
            },
        )
    }

    override fun getDashboardSnapshot(competence: CompetenceMonth): Flow<DashboardSnapshot> = flow {
        emitAll(
            combine(
                dashboardSnapshotDao.observeSnapshot(
                    year = competence.year,
                    month = competence.month.ordinal + 1,
                ),
                dashboardSnapshotDao.observeTransactions(
                    year = competence.year,
                    month = competence.month.ordinal + 1,
                ),
            ) { snapshot, transactions ->
                snapshot?.toDomain(transactions)
                    ?: emptyDashboardSnapshotFor(competence, lastUpdatedAt = currentTimestamp())
            },
        )
    }
}

private fun CompetenceMonth.absoluteMonthIndex(): Int = year * 12 + month.ordinal
