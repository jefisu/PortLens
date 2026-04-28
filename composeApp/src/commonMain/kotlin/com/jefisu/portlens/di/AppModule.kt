package com.jefisu.portlens.di

import com.jefisu.portlens.fakeGetAvailableCompetences
import com.jefisu.portlens.fakeGetDashboardSnapshot
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModule = module {
    singleOf(::fakeGetAvailableCompetences)
    singleOf(::fakeGetDashboardSnapshot)
}
