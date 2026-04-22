package com.jefisu.portlens.core.domain

fun interface GetAvailableCompetences {
    suspend operator fun invoke(currentLocalCompetence: CompetenceMonth): List<CompetenceMonth>
}
