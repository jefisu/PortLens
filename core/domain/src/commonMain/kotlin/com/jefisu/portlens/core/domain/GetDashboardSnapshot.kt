package com.jefisu.portlens.core.domain

fun interface GetDashboardSnapshot {
    suspend operator fun invoke(competence: CompetenceMonth): DashboardSnapshot
}
