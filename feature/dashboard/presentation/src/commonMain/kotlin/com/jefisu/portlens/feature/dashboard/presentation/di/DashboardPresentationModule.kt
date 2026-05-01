package com.jefisu.portlens.feature.dashboard.presentation.di

import com.jefisu.portlens.feature.dashboard.presentation.DashboardViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val dashboardPresentationModule = module {
    viewModelOf(::DashboardViewModel)
}
